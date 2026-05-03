package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/9 12:14
 * @author:bjpowernode.com
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        System.out.println("进入了Test请求");
        return "test请求";
    }
}
