package com.bjpowernode.springboot.service;

import org.springframework.stereotype.Component;

/**
 * ClassName:TestService
 * Package:com.bjpowernode.springboot.service
 * Description:
 *
 * @date:2018/10/9 12:29
 * @author:bjpowernode.com
 */
@Component("testService")
public class TestService {

    public void test(){
        System.out.println("这是test方法111111111111");
    }
}
