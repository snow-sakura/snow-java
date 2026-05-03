package com.bjpowernode.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:HelloController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/8 9:57
 * @author:bjpowernode.com
 */
@Controller
public class HelloController {
    @RequestMapping("/boot/hello")
    public @ResponseBody String hello(){

        return "Hello SpringBoot !";
    }
}
