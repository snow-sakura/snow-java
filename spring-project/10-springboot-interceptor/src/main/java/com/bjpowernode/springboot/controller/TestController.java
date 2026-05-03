package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/9 11:37
 * @author:bjpowernode.com
 */
@RestController
public class TestController {

    @RequestMapping("/public/login")
    public String login(HttpSession session){
        session.setAttribute("userSession","张三");
        return "登录成功" ;
    }
    @RequestMapping("/private/add")
    public String add(){
        System.out.println("进入了add方法");
        return "添加成功";
    }
}
