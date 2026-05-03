package com.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.PageResult;
import com.shop.dto.ProductVO;
import com.shop.entity.Product;
import com.shop.entity.ProductCategory;
import com.shop.mapper.ProductCategoryMapper;
import com.shop.mapper.ProductMapper;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 * 
 * @author Lingma
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductMapper productMapper;
    private final ProductCategoryMapper categoryMapper;
    
    @Override
    public PageResult<ProductVO> getProductPage(Long current, Long size, Long categoryId, String keyword) {
        // 构建查询条件
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, 1); // 只查询上架商品
        
        if (categoryId != null) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.like(Product::getName, keyword);
        }
        
        wrapper.orderByDesc(Product::getCreateTime);
        
        // 分页查询
        Page<Product> page = new Page<>(current, size);
        Page<Product> result = productMapper.selectPage(page, wrapper);
        
        // 转换为VO
        List<ProductVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.build(result.getTotal(), result.getCurrent(), result.getSize(), voList);
    }
    
    @Override
    public ProductVO getProductById(Long productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return null;
        }
        return convertToVO(product);
    }
    
    @Override
    public void increaseSales(Long productId, Integer quantity) {
        Product product = productMapper.selectById(productId);
        if (product != null) {
            product.setSales(product.getSales() + quantity);
            productMapper.updateById(product);
        }
    }
    
    /**
     * 转换为VO对象
     */
    private ProductVO convertToVO(Product product) {
        ProductVO vo = BeanUtil.copyProperties(product, ProductVO.class);
        
        // 获取分类名称
        if (product.getCategoryId() != null) {
            ProductCategory category = categoryMapper.selectById(product.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }
        
        return vo;
    }
}
