package com.shop.controller;

import com.shop.common.PageResult;
import com.shop.common.Result;
import com.shop.entity.Order;
import com.shop.service.OrderService;
import com.shop.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 * 
 * @author Lingma
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    
    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<String> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> params) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        List<Long> cartIds = (List<Long>) params.get("cartIds");
        String receiverName = (String) params.get("receiverName");
        String receiverPhone = (String) params.get("receiverPhone");
        String receiverAddress = (String) params.get("receiverAddress");
        String remark = (String) params.get("remark");
        
        String orderNo = orderService.createOrder(userId, cartIds, receiverName, 
                receiverPhone, receiverAddress, remark);
        
        return Result.success("订单创建成功", orderNo);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/detail")
    public Result<Order> getOrderDetail(@RequestParam String orderNo) {
        Order order = orderService.getOrderById(orderNo);
        return Result.success(order);
    }
    
    /**
     * 分页查询用户订单列表
     */
    @GetMapping("/list")
    public Result<PageResult<Order>> getUserOrders(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Integer status) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        PageResult<Order> orders = orderService.getUserOrders(userId, current, size, status);
        return Result.success(orders);
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/cancel")
    public Result<Void> cancelOrder(
            @RequestHeader("Authorization") String token,
            @RequestParam String orderNo) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        orderService.cancelOrder(userId, orderNo);
        return Result.success("订单已取消", null);
    }
    
    /**
     * 确认收货
     */
    @PutMapping("/confirm")
    public Result<Void> confirmReceipt(
            @RequestHeader("Authorization") String token,
            @RequestParam String orderNo) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        orderService.confirmReceipt(userId, orderNo);
        return Result.success("确认收货成功", null);
    }
}
