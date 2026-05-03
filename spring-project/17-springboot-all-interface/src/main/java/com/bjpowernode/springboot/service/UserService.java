package com.bjpowernode.springboot.service;

import com.bjpowernode.springboot.model.User;

import java.util.List;

/**
 * ClassName:UserService
 * Package:com.bjpowernode.springboot.service
 * Description:
 *
 * @date:2018/10/11 11:36
 * @author:bjpowernode.com
 */
public interface UserService {
    List<User> selectAll();

    User selectUserById(Integer id);

    void updateUser(User user);

    void delete(Integer id);
}
