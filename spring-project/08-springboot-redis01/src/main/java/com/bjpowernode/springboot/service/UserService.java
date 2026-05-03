package com.bjpowernode.springboot.service;

import com.bjpowernode.springboot.model.User;

import java.util.List;

/**
 * ClassName:UserService
 * Package:com.bjpowernode.springboot.service
 * Description:
 *
 * @date:2018/10/9 9:18
 * @author:bjpowernode.com
 */
public interface UserService {
    List<User> selectAll();
}
