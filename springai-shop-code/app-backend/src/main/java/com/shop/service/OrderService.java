package com.shop.service;

import com.shop.common.PageResult;
import com.shop.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 * 
 * @author Lingma
 */
public interface OrderService {
    
    /**
     * 创建订单
     * 
     * @param userId 用户ID
     * @param cartIds 购物车ID列表
     * @param receiverName 收货人姓名
     * @param receiverPhone 收货人电话
     * @param receiverAddress 收货地址
     * @param remark 备注
     * @return 订单号
     */
    String createOrder(Long userId, List<Long> cartIds, String receiverName, 
                      String receiverPhone, String receiverAddress, String remark);
    
    /**
     * 根据订单号获取订单详情
     * 
     * @param orderNo 订单号
     * @return 订单详情
     */
    Order getOrderById(String orderNo);
    
    /**
     * 分页查询用户订单列表
     * 
     * @param userId 用户ID
     * @param current 当前页
     * @param size 每页大小
     * @param status 订单状态
     * @return 分页结果
     */
    PageResult<Order> getUserOrders(Long userId, Long current, Long size, Integer status);
    
    /**
     * 取消订单
     * 
     * @param userId 用户ID
     * @param orderNo 订单号
     */
    void cancelOrder(Long userId, String orderNo);
    
    /**
     * 确认收货
     * 
     * @param userId 用户ID
     * @param orderNo 订单号
     */
    void confirmReceipt(Long userId, String orderNo);
}
