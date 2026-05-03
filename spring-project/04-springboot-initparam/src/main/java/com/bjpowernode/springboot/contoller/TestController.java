package com.bjpowernode.springboot.contoller;

import com.bjpowernode.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.contoller
 * Description:
 *
 * @date:2018/10/8 10:58
 * @author:bjpowernode.com
 */
@Controller
public class TestController {

    @Value("${bjpowernode.name}")
    private String name;
    @Value("${bjpowernode.age}")
    private Integer age;
    @Autowired
    private User user;
    @RequestMapping("/boot/test")
    public @ResponseBody
    String test(){
//        user=new User();
        return"name="+name+"   age="+age+"   user="+user;
    }
}
