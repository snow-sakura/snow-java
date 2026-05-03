package com.bjpowernode.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/8 11:40
 * @author:bjpowernode.com
 */
@Controller
public class TestController {


    @RequestMapping("/boot/index")
    public String index(){

        return "index";
    }



}
