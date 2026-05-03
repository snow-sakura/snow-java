package com.shop.service;

import com.shop.dto.CartVO;

import java.util.List;

/**
 * 购物车服务接口
 * 
 * @author Lingma
 */
public interface CartService {
    
    /**
     * 添加商品到购物车
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     * @param quantity 数量
     */
    void addToCart(Long userId, Long productId, Integer quantity);
    
    /**
     * 更新购物车商品数量
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     * @param quantity 数量
     */
    void updateQuantity(Long userId, Long productId, Integer quantity);
    
    /**
     * 删除购物车商品
     * 
     * @param userId 用户ID
     * @param productId 商品ID
     */
    void removeFromCart(Long userId, Long productId);
    
    /**
     * 获取用户购物车列表
     * 
     * @param userId 用户ID
     * @return 购物车列表
     */
    List<CartVO> getCartList(Long userId);
    
    /**
     * 清空购物车
     * 
     * @param userId 用户ID
     */
    void clearCart(Long userId);
}
