package com.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品VO类(视图对象)
 * 
 * @author Lingma
 */
@Data
public class ProductVO {
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 副标题
     */
    private String subtitle;
    
    /**
     * 主图URL
     */
    private String mainImage;
    
    /**
     * 副图URL列表
     */
    private String subImages;
    
    /**
     * 商品详情
     */
    private String detail;
    
    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * 库存
     */
    private Integer stock;
    
    /**
     * 销量
     */
    private Integer sales;
    
    /**
     * 状态: 0-下架, 1-上架
     */
    private Integer status;
}
