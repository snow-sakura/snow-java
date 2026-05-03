package com.bjpowernode.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/9 11:09
 * @author:bjpowernode.com
 */
@Controller
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/boot/add")
    public @ResponseBody    String addUser(){
        User user=new User();
        user.setName("张飞");
        user.setAge(26);
        userService.insertUser(user);
        return "用户添加成功";
    }
}
