package com.snow.usermgt;

import com.snow.usermgt.config.DatabaseInitializer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用程序主类
 * 
 * @MapperScan 注解用于扫描指定包下的所有Mapper接口
 * @SpringBootApplication 启用Spring Boot的自动配置、组件扫描和配置类功能
 */
@SpringBootApplication
@MapperScan("com.snow.usermgt.mapper") // 扫描Mapper接口所在的包
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    @Autowired
    private DatabaseInitializer databaseInitializer;

    /**
     * 应用程序入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("开始初始化数据库...");
        try {
            databaseInitializer.initializeDatabase();
            logger.info("数据库初始化完成");
        } catch (Exception e) {
            logger.error("数据库初始化失败", e);
            throw e;
        }
    }
}