package com.bjpowernode.springboot;

import com.bjpowernode.springboot.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context= SpringApplication.run(Application.class, args);
        TestService service= (TestService) context.getBean("testService");
        service.test();
    }
}
