package com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车VO类(包含商品信息)
 * 
 * @author Lingma
 */
@Data
public class CartVO {
    
    /**
     * 购物车ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 商品ID
     */
    private Long productId;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 商品图片
     */
    private String productImage;
    
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    
    /**
     * 数量
     */
    private Integer quantity;
    
    /**
     * 是否选中
     */
    private Integer checked;
    
    /**
     * 小计
     */
    private BigDecimal totalPrice;
}
