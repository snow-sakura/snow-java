package com.bjpowernode.activemq;

import com.bjpowernode.activemq.springboo.Receive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
       ApplicationContext context= SpringApplication.run(Application.class, args);
//       Receive receive= (Receive) context.getBean("receive");
//        receive.receive();
    }
}
