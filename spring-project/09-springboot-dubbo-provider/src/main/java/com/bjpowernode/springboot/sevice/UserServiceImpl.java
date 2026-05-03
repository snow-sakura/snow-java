package com.bjpowernode.springboot.sevice;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.springboot.sevice
 * Description:
 *
 * @date:2018/10/9 10:57
 * @author:bjpowernode.com
 */
@Service(interfaceClass=UserService.class)//Dubbo的注解用于暴露服务
@Component("userService")//Spring的注解用于将当前类在Spring容器中创建对象
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
