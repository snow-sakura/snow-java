package com.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 商城系统后端启动类
 * 
 * @author Lingma
 */
@SpringBootApplication
@MapperScan("com.shop.mapper")
public class ShopApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
        System.out.println("========================================");
        System.out.println("   商城系统后端启动成功!");
        System.out.println("   API地址: http://localhost:8080/api");
        System.out.println("========================================");
    }
}
