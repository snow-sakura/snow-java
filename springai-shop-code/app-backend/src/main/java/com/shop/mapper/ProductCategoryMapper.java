package com.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类Mapper接口
 * 
 * @author Lingma
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
}
