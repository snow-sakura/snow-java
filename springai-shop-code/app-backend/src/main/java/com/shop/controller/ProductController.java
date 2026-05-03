package com.shop.controller;

import com.shop.common.PageResult;
import com.shop.common.Result;
import com.shop.dto.ProductVO;
import com.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 * 
 * @author Lingma
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "商品查询、详情等相关接口")
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * 分页查询商品列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询商品列表", description = "支持按分类和关键词搜索商品")
    public Result<PageResult<ProductVO>> getProductPage(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Long current,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") Long size,
            @Parameter(description = "分类ID")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "搜索关键词")
            @RequestParam(required = false) String keyword) {
        
        PageResult<ProductVO> page = productService.getProductPage(current, size, categoryId, keyword);
        return Result.success(page);
    }
    
    /**
     * 获取商品详情
     */
    @GetMapping("/{productId}")
    @Operation(summary = "获取商品详情", description = "根据商品ID获取详细信息")
    public Result<ProductVO> getProductDetail(
            @Parameter(description = "商品ID", example = "1") 
            @PathVariable Long productId) {
        ProductVO product = productService.getProductById(productId);
        return Result.success(product);
    }
}
