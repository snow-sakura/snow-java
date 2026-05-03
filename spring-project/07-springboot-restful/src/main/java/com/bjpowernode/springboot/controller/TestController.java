package com.bjpowernode.springboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/8 12:24
 * @author:bjpowernode.com
 */
@RestController
public class TestController {

    @RequestMapping("/boot/{name}/张三/{age}")
    public @ResponseBody String test(@PathVariable("name") String name,@PathVariable("age") Integer age){

        return "name="+name+"   age="+age;
    }
}
