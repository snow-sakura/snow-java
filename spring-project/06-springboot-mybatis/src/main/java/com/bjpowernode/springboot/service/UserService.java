package com.bjpowernode.springboot.service;

import com.bjpowernode.springboot.model.User;

import java.util.List;

/**
 * ClassName:UserService
 * Package:com.bjpowernode.springboot.service
 * Description:
 *
 * @date:2018/10/8 12:04
 * @author:bjpowernode.com
 */
public interface UserService {
    void addUser(User user);

    List<User> selectAll();
}
