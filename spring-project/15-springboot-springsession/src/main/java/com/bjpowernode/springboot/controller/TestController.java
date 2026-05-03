package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/10 10:21
 * @author:bjpowernode.com
 */
@RestController
public class TestController {
    @RequestMapping("/set")
    public String setSessionData(HttpSession session){
        session.setAttribute("sessionKey","Session Data!");
        return "Session Set Ok!";
    }
}
