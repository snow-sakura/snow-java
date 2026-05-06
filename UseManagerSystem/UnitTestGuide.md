# Spring Boot项目单元测试完整指南

## 项目概述
这是一个基于Spring Boot、MyBatis和MySQL的用户管理系统，采用三层架构（Controller、Service、Mapper）。本指南展示了如何为每个层次编写全面的单元测试，涵盖增删改查（CRUD）操作。

## 已完成的单元测试代码

### 1. Service层测试 - UserServiceTest.java

#### 功能覆盖
- 用户注册（CREATE）
- 用户登录验证
- 查询所有用户（READ）
- 根据ID查询用户（READ）
- 根据手机号查询用户（READ）
- 更新用户信息（UPDATE）
- 删除用户（DELETE）
- 异常情况测试（如重复手机号、无效用户等）

#### 关键测试示例

```java
/**
 * 测试用户注册功能
 */
@Test
public void testRegister() {
    // 准备测试数据
    String phone = "13712345678";
    String nickname = "测试用户";
    String password = "123456";

    // 执行注册操作
    User user = userService.register(phone, nickname, password);

    // 验证结果
    assertNotNull(user.getId());
    assertEquals(phone, user.getPhone());
    assertEquals(nickname, user.getNickname());
    assertNotNull(user.getCreateTime());
}

/**
 * 测试更新用户信息功能
 */
@Test
public void testUpdateUser() {
    // 准备测试数据
    String originalPhone = "14812345678";
    String newPhone = "14712345678";
    String originalNickname = "原始昵称";
    String newNickname = "更新后昵称";
    String password = "123456";

    // 注册用户
    User user = userService.register(originalPhone, originalNickname, password);

    // 更新用户信息
    user.setPhone(newPhone);
    user.setNickname(newNickname);

    // 执行更新操作
    int result = userService.updateUser(user);

    // 验证更新结果
    assertEquals(1, result); // 应该成功更新一条记录
    
    // 从数据库重新查询验证更新是否生效
    User updatedUser = userService.findUserById(user.getId());
    assertNotNull(updatedUser);
    assertEquals(newPhone, updatedUser.getPhone());
    assertEquals(newNickname, updatedUser.getNickname());
}
```

### 2. Mapper层测试 - UserMapperTest.java

#### 功能覆盖
- 插入用户（CREATE）
- 根据ID查找用户（READ）
- 根据手机号查找用户（READ）
- 查找所有用户（READ）
- 更新用户信息（UPDATE）
- 删除用户（DELETE）

#### 关键测试示例

```java
/**
 * 测试插入用户功能 - CREATE操作
 */
@Test
public void testInsertUser() {
    // 准备测试数据
    User user = new User();
    user.setPhone("13812345678");
    user.setNickname("测试用户");
    user.setPassword("encrypted_password");
    user.setCreateTime(LocalDateTime.now());

    // 执行插入操作
    int result = userMapper.insert(user);

    // 验证结果
    assertEquals(1, result); // 确认有一条记录被插入
    assertNotNull(user.getId()); // 确认ID被自动生成
    
    // 验证用户确实被保存到数据库中
    User savedUser = userMapper.findById(user.getId());
    assertNotNull(savedUser);
    assertEquals("13812345678", savedUser.getPhone());
    assertEquals("测试用户", savedUser.getNickname());
}
```

### 3. Controller层测试 - UserControllerTest.java

#### 功能覆盖
- 用户注册API端点测试（CREATE）
- 用户登录API端点测试
- 查询所有用户API端点测试（READ）
- 根据ID查询用户API端点测试（READ）
- 根据手机号查询用户API端点测试（READ）
- 更新用户信息API端点测试（UPDATE）
- 删除用户API端点测试（DELETE）
- 错误处理和异常响应测试

#### 关键测试示例

```java
/**
 * 测试用户注册功能 - CREATE操作
 */
@Test
public void testRegister() throws Exception {
    // 准备测试数据
    String phone = "13812345678";
    String nickname = "测试注册用户";
    String password = "123456";
    
    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setPhone(phone);
    mockUser.setNickname(nickname);
    mockUser.setPassword("encrypted_password");
    mockUser.setCreateTime(LocalDateTime.now());

    // 模拟服务层返回
    when(userService.register(eq(phone), eq(nickname), eq(password))).thenReturn(mockUser);

    // 执行POST请求
    mockMvc.perform(post("/api/users/register")
            .param("phone", phone)
            .param("nickname", nickname)
            .param("password", password)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("注册成功"))
            .andExpect(jsonPath("$.data.phone").value(phone))
            .andExpect(jsonPath("$.data.nickname").value(nickname));

    // 验证服务层方法被调用
    verify(userService).register(eq(phone), eq(nickname), eq(password));
}
```

## 测试策略详解

### 1. Service层测试策略
- **集成测试**：使用@SpringBootTest加载整个Spring上下文
- **数据隔离**：每个测试方法使用唯一的测试数据，避免相互干扰
- **边界测试**：测试各种异常情况（如重复注册、无效用户等）
- **业务逻辑验证**：验证核心业务规则（如密码加密、唯一性约束等）

### 2. Mapper层测试策略
- **数据访问验证**：验证SQL语句的执行结果
- **事务测试**：确保数据库操作的原子性
- **关联查询**：验证复杂查询的正确性
- **性能测试**：验证查询效率

### 3. Controller层测试策略
- **Mock服务层**：使用@MockBean模拟服务层依赖
- **API契约测试**：验证请求参数和响应格式
- **状态码验证**：验证HTTP状态码的正确性
- **安全测试**：验证认证和授权机制

## 测试最佳实践

### 1. 测试命名规范
- 使用描述性的测试方法名
- 遵循given-when-then模式
- 包含预期结果的描述

### 2. 测试数据管理
- 使用独立的测试数据库
- 测试前后清理数据
- 避免测试间的数据依赖

### 3. 断言策略
- 验证关键业务属性
- 使用适当的数据比较方法
- 包含有意义的错误消息

## 项目配置说明

### 1. 依赖配置（pom.xml）
```xml
<!-- H2 Database for testing -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- MyBatis Test Starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter-test</artifactId>
    <version>3.0.3</version>
    <scope>test</scope>
</dependency>
```

### 2. 测试配置（application-test.properties）
```properties
# 测试环境数据库配置 - 使用H2内存数据库
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# MyBatis配置
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.type-aliases-package=com.snow.usermgt.entity
mybatis.mapper-locations=classpath*:mapper/*.xml
```

## 总结

本套单元测试代码覆盖了Spring Boot + MyBatis + MySQL项目的完整三层架构，提供了全面的CRUD操作测试。测试代码具有以下特点：

1. **全面性**：覆盖所有核心业务功能
2. **独立性**：测试之间互不干扰
3. **可维护性**：清晰的代码结构和注释
4. **可靠性**：包含异常情况和边界条件测试
5. **实用性**：可直接用于生产项目

这些测试代码可以作为类似项目的模板，帮助开发者快速建立高质量的单元测试体系。