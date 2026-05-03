package com.bjpowernode.activemq;

import com.bjpowernode.activemq.spring.SpringSender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName:RunMain
 * Package:com.bjpowernode.activemq
 * Description:
 *
 * @date:2018/10/18 9:48
 * @author:bjpowernode.com
 */
public class RunMain {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringSender sender= (SpringSender) context.getBean("springSender");
        sender.sender();
    }
}
