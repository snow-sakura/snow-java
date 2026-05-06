用户管理系统 (User Management System)
================================

项目概述：
本项目是一个基于Spring Boot和MyBatis的用户管理系统，提供了用户注册、登录和查询功能。

技术栈：
- Spring Boot 3.2.0
- MyBatis 3.0.2
- MySQL 8.0+
- Java 21
- Maven 3.6+

功能特性：
1. 用户注册 - 提供手机号注册功能，密码自动加密存储
2. 用户登录 - 验证手机号和密码
3. 用户查询 - 支持查询所有用户、根据ID查询用户、根据手机号查询用户

环境依赖：
1. Java 21 或更高版本
2. Maven 3.6 或更高版本
3. MySQL 5.7 或更高版本
4. Git (可选，用于版本控制)

项目启动步骤：

1. 数据库配置：
   - 安装并启动MySQL服务
   - 本项目支持自动创建数据库和表结构，无需手动创建
   - 默认数据库配置：
     - HOSTNAME: localhost
     - PORT: 3306
     - USERNAME: root
     - PASSWORD: wuxuehua123456
     - DATABASE: user_management

   注意事项：
   - 请确保MySQL服务正在运行
   - 如果使用不同的数据库配置，请相应修改src/main/resources/application.properties文件
   - 如遇数据库连接问题，请确认MySQL版本兼容性及驱动配置

2. 项目启动方式：
   方式一：使用批处理文件
   - 确保环境满足要求
   - 双击运行 run.bat 文件
   - 程序将自动构建并启动

   方式二：使用Maven命令
   - 打开命令行工具
   - 进入项目根目录 (UseManagerSystem)
   - 运行命令：mvn clean install -DskipTests
   - 运行命令：mvn spring-boot:run

   方式三：从主类启动 (推荐开发模式)
   - 在IDE中打开项目
   - 找到主类：com.snow.usermgt.Application
   - 运行 Application.java 中的 main 方法
   - 程序将自动创建数据库并启动应用

2. 项目构建：
   - 打开终端或命令提示符
   - 进入项目根目录 (UseManagerSystem)
   - 运行命令：mvn clean install

3. 项目启动：
   - 进入项目根目录 (UseManagerSystem)
   - 运行命令：mvn spring-boot:run
   - 或者打包后运行：mvn package && java -jar target/UseManagerSystem-1.0.0.jar

4. 项目访问：
   - 应用启动后，默认端口为8080
   - 访问 http://localhost:8080 查看应用状态

API接口说明：
1. 用户注册：
   - 接口：POST /api/users/register
   - 参数：phone(手机号), nickname(昵称), password(密码)
   - 示例：POST /api/users/register?phone=13812345678&nickname=新用户&password=123456

2. 用户登录：
   - 接口：POST /api/users/login
   - 参数：phone(手机号), password(密码)
   - 示例：POST /api/users/login?phone=13812345678&password=123456

3. 查询所有用户：
   - 接口：GET /api/users
   - 示例：GET /api/users

4. 根据ID查询用户：
   - 接口：GET /api/users/{id}
   - 示例：GET /api/users/1

5. 根据手机号查询用户：
   - 接口：GET /api/users/phone/{phone}
   - 示例：GET /api/users/phone/13812345678

注意事项：
1. 确保MySQL服务已启动并能正常连接
2. 确保数据库用户名密码正确
3. 确保端口8080未被其他应用占用
4. 密码在数据库中以MD5加密形式存储
5. 手机号格式验证为：1开头，第二位为3-9，共11位数字

测试说明：
项目包含完整的单元测试，可以通过以下命令运行：
mvn test

项目结构：
UseManagerSystem/
├── src/
│   ├── main/
│   │   ├── java/com/snow/usermgt/
│   │   │   ├── Application.java          # 主应用类
│   │   │   ├── controller/               # 控制器层
│   │   │   │   └── UserController.java
│   │   │   ├── service/                  # 服务层
│   │   │   │   └── UserService.java
│   │   │   ├── mapper/                   # 数据访问层
│   │   │   │   └── UserMapper.java
│   │   │   └── entity/                   # 实体类
│   │   │       └── User.java
│   │   └── resources/
│   │       ├── application.properties    # 应用配置文件
│   │       └── schema.sql                # 数据库初始化脚本
│   └── test/                             # 测试代码
└── pom.xml                               # Maven配置文件