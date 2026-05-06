package com.snow.usermgt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库初始化配置类
 * 
 * 负责在应用启动时自动创建数据库
 */
@Component
public class DatabaseInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * 初始化数据库
     * 检查并创建数据库（如果不存在）
     */
    public void initializeDatabase() {
        // 从datasourceUrl中提取基础URL（不包含数据库名）
        String baseUrl = extractBaseUrl(datasourceUrl);
        
        try {
            // 直接连接到MySQL服务器而不指定数据库
            try (Connection connection = DriverManager.getConnection(baseUrl, datasourceUsername, datasourcePassword)) {
                try (Statement statement = connection.createStatement()) {
                    // 检查数据库是否存在，如果不存在则创建
                    String createDbSql = "CREATE DATABASE IF NOT EXISTS user_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
                    statement.executeUpdate(createDbSql);
                    logger.info("数据库创建或已存在: user_management");
                }
            }
            
            // 现在确保数据库存在，尝试连接到数据库并执行表初始化
            initializeTables();
        } catch (SQLException e) {
            logger.error("数据库初始化失败", e);
            throw new RuntimeException("数据库初始化失败", e);
        }
    }
    
    /**
     * 初始化数据库表
     */
    private void initializeTables() {
        try {
            // 使用JdbcTemplate执行schema.sql中的表创建语句
            Resource resource = new ClassPathResource("schema.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
            logger.info("数据库表结构创建完成");
            
            // 执行data.sql中的数据插入语句
            Resource dataResource = new ClassPathResource("data.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), dataResource);
            logger.info("数据库初始数据插入完成");
            
        } catch (Exception e) {
            logger.warn("表初始化失败，可能是因为表已存在: " + e.getMessage());
            // 如果表已存在，这通常不是致命错误
        }
    }
    
    /**
     * 从完整的数据库URL中提取基础URL（去掉数据库名和特定参数）
     */
    private String extractBaseUrl(String fullUrl) {
        // 移除URL中的数据库名部分，例如从jdbc:mysql://localhost:3306/dbname移除/dbname
        int lastSlashIndex = fullUrl.lastIndexOf("/");
        if (lastSlashIndex > "jdbc:mysql:".length()) {
            return fullUrl.substring(0, lastSlashIndex);
        }
        return fullUrl;
    }
}