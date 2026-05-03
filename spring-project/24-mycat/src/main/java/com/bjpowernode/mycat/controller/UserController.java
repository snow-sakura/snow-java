package com.bjpowernode.mycat.controller;

import com.bjpowernode.mycat.mapper.UserMapper;
import com.bjpowernode.mycat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.mycat.controller
 * Description:
 *
 * @date:2018/10/22 9:38
 * @author:bjpowernode.com
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/selectAll")
    public Object selectAll(){
        List<User> list=userMapper.selectAll();
        return list;
    }

    @RequestMapping("/insert")
    public String insert(){
        User user=new User();
        user.setName("张三");
       userMapper.insert(user);
        return "添加成功";
    }
}
