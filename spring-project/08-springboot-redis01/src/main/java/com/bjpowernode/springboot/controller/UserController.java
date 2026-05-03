package com.bjpowernode.springboot.controller;

import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/9 9:15
 * @author:bjpowernode.com
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/boot/select")
    public @ResponseBody
    List<User> select() {


        List<User> list = null;//  userService.selectAll();
        //定义一个拥有20个并发的线程池对象
        ExecutorService service = Executors.newFixedThreadPool(20);
        //定义一个最终执行任务的线程对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                userService.selectAll();
            }
        };

        for (int i = 0; i < 100; i++) {
            //提交线程，并并发执行
            service.submit(runnable);
        }


        return list;
    }
}
