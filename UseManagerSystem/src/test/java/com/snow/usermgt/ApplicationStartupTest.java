package com.snow.usermgt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 应用启动测试类
 * 
 * 验证应用程序能够正常启动，包括数据库初始化
 */
@SpringBootTest
class ApplicationStartupTest {

    @Test
    void contextLoads() {
        // 验证Spring应用上下文能够正常加载
        // 这个测试会在应用启动时运行，验证所有配置正确
    }
}