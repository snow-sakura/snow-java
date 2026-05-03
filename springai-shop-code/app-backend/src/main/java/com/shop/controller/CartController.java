package com.shop.controller;

import com.shop.common.Result;
import com.shop.dto.CartVO;
import com.shop.service.CartService;
import com.shop.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车控制器
 * 
 * @author Lingma
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "购物车管理", description = "购物车增删改查相关接口")
public class CartController {
    
    private final CartService cartService;
    private final JwtUtil jwtUtil;
    
    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    @Operation(summary = "添加商品到购物车", description = "需要登录，从Token获取用户ID")
    public Result<Void> addToCart(
            @Parameter(description = "JWT Token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "商品ID", example = "1")
            @RequestParam Long productId,
            @Parameter(description = "数量", example = "1")
            @RequestParam(defaultValue = "1") Integer quantity) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        cartService.addToCart(userId, productId, quantity);
        return Result.success("添加成功", null);
    }
    
    /**
     * 更新购物车商品数量
     */
    @PutMapping("/update")
    @Operation(summary = "更新购物车商品数量", description = "需要登录")
    public Result<Void> updateQuantity(
            @Parameter(description = "JWT Token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "商品ID", example = "1")
            @RequestParam Long productId,
            @Parameter(description = "数量", example = "2")
            @RequestParam Integer quantity) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        cartService.updateQuantity(userId, productId, quantity);
        return Result.success("更新成功", null);
    }
    
    /**
     * 删除购物车商品
     */
    @DeleteMapping("/remove")
    @Operation(summary = "删除购物车商品", description = "需要登录")
    public Result<Void> removeFromCart(
            @Parameter(description = "JWT Token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "商品ID", example = "1")
            @RequestParam Long productId) {
        
        Long userId = jwtUtil.getUserIdFromToken(token);
        cartService.removeFromCart(userId, productId);
        return Result.success("删除成功", null);
    }
    
    /**
     * 获取购物车列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取购物车列表", description = "需要登录")
    public Result<List<CartVO>> getCartList(
            @Parameter(description = "JWT Token", required = true)
            @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        List<CartVO> list = cartService.getCartList(userId);
        return Result.success(list);
    }
    
    /**
     * 清空购物车
     */
    @DeleteMapping("/clear")
    @Operation(summary = "清空购物车", description = "需要登录")
    public Result<Void> clearCart(
            @Parameter(description = "JWT Token", required = true)
            @RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        cartService.clearCart(userId);
        return Result.success("清空成功", null);
    }
}
