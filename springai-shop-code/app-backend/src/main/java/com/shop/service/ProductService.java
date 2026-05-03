package com.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.PageResult;
import com.shop.dto.ProductVO;
import com.shop.entity.Product;

/**
 * 商品服务接口
 * 
 * @author Lingma
 */
public interface ProductService {
    
    /**
     * 分页查询商品列表
     * 
     * @param current 当前页
     * @param size 每页大小
     * @param categoryId 分类ID
     * @param keyword 搜索关键词
     * @return 分页结果
     */
    PageResult<ProductVO> getProductPage(Long current, Long size, Long categoryId, String keyword);
    
    /**
     * 根据ID获取商品详情
     * 
     * @param productId 商品ID
     * @return 商品详情
     */
    ProductVO getProductById(Long productId);
    
    /**
     * 增加商品销量
     * 
     * @param productId 商品ID
     * @param quantity 数量
     */
    void increaseSales(Long productId, Integer quantity);
}
