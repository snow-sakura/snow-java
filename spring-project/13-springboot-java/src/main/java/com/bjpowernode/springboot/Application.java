package com.bjpowernode.springboot;

import com.bjpowernode.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private TestService testService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //这个run方法不能手动调用，SpringBoot启动后会自定调用这个方法，这个方法会在Spring的容器中存放
    //如果手动调用run方法，Spring的自动注入将失效
    @Override
    public void run(String... args) throws Exception {
        testService.test();
    }
}
