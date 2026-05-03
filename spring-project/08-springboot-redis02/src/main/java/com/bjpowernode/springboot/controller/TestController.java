package com.bjpowernode.springboot.controller;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/9 10:26
 * @author:bjpowernode.com
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/boot/setData")
    public @ResponseBody String test(){
        for(int i=0;i>=0;i++){
            redisTemplate.opsForValue().set("k"+i,"v"+i);
            System.out.println("key=k"+i+"   value=v"+i);
        }

        return "数据设置成功";
    }

}
