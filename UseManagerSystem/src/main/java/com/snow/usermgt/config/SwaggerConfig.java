package com.snow.usermgt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 
 * 配置API文档生成参数
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("用户管理系统API")
                        .version("1.0")
                        .description("用户管理系统的RESTful API接口文档")
                        .contact(new Contact()
                                .name("Snow")
                                .email("snow@example.com")
                                .url("https://github.com/snow/user-management")));
    }
}