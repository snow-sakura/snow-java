package com.bjpowernode.masterslave;

import com.bjpowernode.masterslave.service.StuService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName:RunMain
 * Package:com.bjpowernode.masterslave
 * Description:
 *
 * @date:2018/10/20 9:32
 * @author:bjpowernode.com
 */
public class RunMain {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        StuService stuService= (StuService) context.getBean("stuService");
        stuService.addStu();
        stuService.select();
    }
}
