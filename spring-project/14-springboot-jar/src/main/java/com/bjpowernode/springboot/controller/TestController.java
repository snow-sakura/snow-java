package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/10 9:39
 * @author:bjpowernode.com
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test() {

        return "这是Jar包的SpringBoot项目";
    }
}
