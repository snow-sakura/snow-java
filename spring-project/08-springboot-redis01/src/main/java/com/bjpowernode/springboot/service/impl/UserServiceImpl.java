package com.bjpowernode.springboot.service.impl;

import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.springboot.service.impl
 * Description:
 *
 * @date:2018/10/9 9:18
 * @author:bjpowernode.com
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<User> selectAll() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        List<User> list = (List<User>) redisTemplate.opsForValue().get("userAll");
        //在高并发情况下会出现缓存穿透问题，原因是当并发请求同时从redis中获取数据时，这个如果redis中没有数据，这些并发请求
        //会同时获取不到，因此会同同时访问访问数据库，并同时向缓存中存入数据
        /*
           解决方案：
             使用双重检查+局部同步锁
             注意：尽量不要使用方法同步
         */
        if (list == null) {//进入if的请求为第一批的同步请求
            synchronized (this) {//第一批请求排队执行
                list = (List<User>) redisTemplate.opsForValue().get("userAll");
                if (list == null) {//进入if的请求为第一批的第一个请求
                    list = userMapper.selectAll();
                    redisTemplate.opsForValue().set("userAll", list);
                    System.out.println("从数据库中获取数据====================");
                } else {
                    System.out.println("从Reids中获取数据----------------------");
                }
            }
        } else {
            System.out.println("从Reids中获取数据----------------------");
        }
        return list;
    }
}
