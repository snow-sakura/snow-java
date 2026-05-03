package com.shop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.PageResult;
import com.shop.entity.*;
import com.shop.exception.BusinessException;
import com.shop.mapper.*;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * 订单服务实现类
 * 
 * @author Lingma
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createOrder(Long userId, List<Long> cartIds, String receiverName,
                             String receiverPhone, String receiverAddress, String remark) {
        // 获取购物车商品
        List<Cart> carts = cartMapper.selectBatchIds(cartIds);
        if (carts == null || carts.isEmpty()) {
            throw new BusinessException("购物车为空");
        }
        
        // 计算订单金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            if (product == null) {
                throw new BusinessException("商品不存在: " + cart.getProductId());
            }
            
            // 检查库存
            if (product.getStock() < cart.getQuantity()) {
                throw new BusinessException("商品库存不足: " + product.getName());
            }
            
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
        }
        
        // 创建订单
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setFreightAmount(BigDecimal.ZERO);
        order.setStatus(0); // 待付款
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        order.setRemark(remark);
        
        orderMapper.insert(order);
        
        // 创建订单项
        for (Cart cart : carts) {
            Product product = productMapper.selectById(cart.getProductId());
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            
            orderItemMapper.insert(orderItem);
            
            // 扣减库存
            product.setStock(product.getStock() - cart.getQuantity());
            productMapper.updateById(product);
        }
        
        // 删除购物车中的商品
        cartMapper.deleteBatchIds(cartIds);
        
        return order.getOrderNo();
    }
    
    @Override
    public Order getOrderById(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        return orderMapper.selectOne(wrapper);
    }
    
    @Override
    public PageResult<Order> getUserOrders(Long userId, Long current, Long size, Integer status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> page = new Page<>(current, size);
        Page<Order> result = orderMapper.selectPage(page, wrapper);
        
        return PageResult.build(result.getTotal(), result.getCurrent(), result.getSize(), result.getRecords());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long userId, String orderNo) {
        Order order = getOrderById(orderNo);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != 0) {
            throw new BusinessException("只能取消待付款订单");
        }
        
        order.setStatus(4); // 已取消
        orderMapper.updateById(order);
        
        // 恢复库存
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItem::getOrderId, order.getId());
        List<OrderItem> orderItems = orderItemMapper.selectList(wrapper);
        
        for (OrderItem item : orderItems) {
            Product product = productMapper.selectById(item.getProductId());
            if (product != null) {
                product.setStock(product.getStock() + item.getQuantity());
                productMapper.updateById(product);
            }
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReceipt(Long userId, String orderNo) {
        Order order = getOrderById(orderNo);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != 2) {
            throw new BusinessException("只能确认已发货的订单");
        }
        
        order.setStatus(3); // 已完成
        order.setFinishTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
    
    /**
     * 生成订单号
     * 格式: yyyyMMddHHmmss + 6位随机数
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;
        return timestamp + randomNum;
    }
}
