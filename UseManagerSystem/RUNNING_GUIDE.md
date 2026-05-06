# 用户管理系统运行指南

## 问题描述
在Windows环境下运行`run.bat`文件时可能出现以下问题：
1. 中文字符乱码
2. 程序启动失败

## 解决方案

### 1. 乱码问题
已在`run.bat`文件中添加`chcp 65001`命令以设置UTF-8编码，解决中文乱码问题。

### 2. 环境依赖检查
确保以下环境已正确安装并配置：
- Java 21 或更高版本
- Maven 3.6 或更高版本
- MySQL 5.7 或更高版本

### 3. 数据库配置
在运行程序前，请确保：
1. MySQL服务正在运行
2. 程序会在启动时自动创建名为`user_management`的数据库（如果不存在）
3. 在`application.properties`中配置正确的数据库用户名和密码
4. 程序会自动创建所需的表结构和初始数据

#### 创建数据库的方法：
```sql
CREATE DATABASE IF NOT EXISTS user_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

或者运行项目中的`src/main/resources/schema.sql`文件来创建数据库和表结构。

### 4. 运行步骤
1. 确保MySQL服务已启动
2. 运行`run.bat`文件
3. 程序将自动构建项目并启动Spring Boot应用
4. 应用将在`http://localhost:8080`上运行

### 5. 常见问题排查
- 如果出现数据库连接错误，请检查MySQL服务是否运行，以及`application.properties`中的数据库配置
- 如果构建失败，请检查Java和Maven环境变量是否正确配置
- 如果端口被占用，请修改`application.properties`中的`server.port`配置

### 6. 应用功能
- 用户注册：POST `/register`
- 用户查询：GET `/users`
- 具体API文档可通过启动应用后访问 `http://localhost:8080/swagger-ui.html` 查看