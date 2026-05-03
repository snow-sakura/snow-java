package com.shop.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring AI配置类
 * 
 * @author Lingma
 */
@Configuration
public class AiConfig {
    
    /**
     * 创建ChatClient Bean
     * Spring AI会自动注入ChatModel并创建ChatClient
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
