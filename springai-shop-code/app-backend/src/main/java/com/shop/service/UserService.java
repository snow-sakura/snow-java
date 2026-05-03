package com.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.dto.LoginRequest;
import com.shop.dto.LoginResponse;
import com.shop.dto.RegisterRequest;
import com.shop.entity.User;

/**
 * 用户服务接口
 * 
 * @author Lingma
 */
public interface UserService {
    
    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户注册
     * 
     * @param request 注册请求
     * @return 用户信息
     */
    User register(RegisterRequest request);
    
    /**
     * 根据ID获取用户
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUserById(Long userId);
    
    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     */
    void updateUser(User user);
}
