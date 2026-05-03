package com.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.dto.CartVO;
import com.shop.entity.Cart;
import com.shop.entity.Product;
import com.shop.exception.BusinessException;
import com.shop.mapper.CartMapper;
import com.shop.mapper.ProductMapper;
import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车服务实现类
 * 
 * @author Lingma
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(Long userId, Long productId, Integer quantity) {
        // 检查商品是否存在
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查库存
        if (product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        
        // 查询是否已存在
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getProductId, productId);
        Cart existingCart = cartMapper.selectOne(wrapper);
        
        if (existingCart != null) {
            // 更新数量
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartMapper.updateById(existingCart);
        } else {
            // 新增
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cart.setChecked(1);
            cartMapper.insert(cart);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }
        
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getProductId, productId);
        Cart cart = cartMapper.selectOne(wrapper);
        
        if (cart == null) {
            throw new BusinessException("购物车中无此商品");
        }
        
        // 检查库存
        Product product = productMapper.selectById(productId);
        if (product != null && product.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        
        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFromCart(Long userId, Long productId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.eq(Cart::getProductId, productId);
        cartMapper.delete(wrapper);
    }
    
    @Override
    public List<CartVO> getCartList(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        List<Cart> carts = cartMapper.selectList(wrapper);
        
        return carts.stream()
                .map(cart -> {
                    CartVO vo = new CartVO();
                    BeanUtil.copyProperties(cart, vo);
                    
                    // 获取商品信息
                    Product product = productMapper.selectById(cart.getProductId());
                    if (product != null) {
                        vo.setProductName(product.getName());
                        vo.setProductImage(product.getMainImage());
                        vo.setProductPrice(product.getPrice());
                        vo.setTotalPrice(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
                    }
                    
                    return vo;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        cartMapper.delete(wrapper);
    }
}
