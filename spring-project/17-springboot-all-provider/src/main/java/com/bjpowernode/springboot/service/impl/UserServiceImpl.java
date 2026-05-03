package com.bjpowernode.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.springboot.service.impl
 * Description:
 *
 * @date:2018/10/11 11:41
 * @author:bjpowernode.com
 */
@Service(interfaceClass = UserService.class)
@Component("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<User> selectAll() {
        List<User>list= (List<User>) redisTemplate.opsForValue().get("userAll");
        if(list==null){
            synchronized (this){
                list= (List<User>) redisTemplate.opsForValue().get("userAll");
                if(list==null){
                    list=userMapper.selectAll();
                    redisTemplate.opsForValue().set("userAll",list);
                }
            }
        }

        return list;
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
        redisTemplate.delete("userAll");
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("userAll");
    }
}
