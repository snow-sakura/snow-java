package com.bjpowernode.springboot.controller;

import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/8 12:02
 * @author:bjpowernode.com
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/boot/add")
    public String add(){
        User user=new User();
        user.setName("张三");
        user.setAge(23);

        userService.addUser(user);


        return "用户添加成功";
    }
    @RequestMapping("/boot/select")
    public List<User> select(){
       List<User>list= userService.selectAll();


        return list;
    }
}
