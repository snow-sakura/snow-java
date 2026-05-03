package com.shop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 
 * @author Lingma
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("优品商城API文档")
                        .version("1.0.0")
                        .description("基于Spring AI的商城系统后端接口文档\n\n" +
                                "## 认证方式\n" +
                                "大部分接口需要通过JWT Token进行认证。\n\n" +
                                "**使用方法:**\n" +
                                "1. 调用 /user/login 接口获取token\n" +
                                "2. 点击页面右上角的 **Authorize** 按钮\n" +
                                "3. 输入格式: `Bearer {token}`")
                        .contact(new Contact()
                                .name("Lingma")
                                .email("lingma@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
