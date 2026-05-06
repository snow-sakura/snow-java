package com.snow.usermgt.service;

import com.snow.usermgt.entity.User;
import com.snow.usermgt.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试类
 * 
 * 测试用户服务的各种功能，包括注册、登录和查询
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

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
     * 测试用户注册时手机号重复的情况
     */
    @Test
    public void testRegisterWithExistingPhone() {
        // 准备测试数据
        String phone = "13612345678";
        String nickname1 = "测试用户1";
        String nickname2 = "测试用户2";
        String password = "123456";

        // 先注册一个用户
        userService.register(phone, nickname1, password);

        // 尝试使用相同手机号注册，应该抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(phone, nickname2, password);
        });

        assertEquals("手机号已存在: " + phone, exception.getMessage());
    }

    /**
     * 测试用户登录功能
     */
    @Test
    public void testLogin() {
        // 准备测试数据
        String phone = "13512345678";
        String nickname = "登录测试用户";
        String password = "123456";

        // 先注册用户
        userService.register(phone, nickname, password);

        // 执行登录操作
        User user = userService.login(phone, password);

        // 验证结果
        assertNotNull(user);
        assertEquals(phone, user.getPhone());
        assertEquals(nickname, user.getNickname());
    }

    /**
     * 测试用户登录时手机号不存在的情况
     */
    @Test
    public void testLoginWithNonExistingPhone() {
        // 尝试使用不存在的手机号登录，应该抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("13412345678", "123456");
        });

        assertEquals("手机号不存在: 13412345678", exception.getMessage());
    }

    /**
     * 测试用户登录时密码错误的情况
     */
    @Test
    public void testLoginWithWrongPassword() {
        // 准备测试数据
        String phone = "13312345678";
        String nickname = "密码错误测试用户";
        String password = "123456";

        // 先注册用户
        userService.register(phone, nickname, password);

        // 使用错误的密码登录，应该抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login(phone, "wrongpassword");
        });

        assertEquals("密码错误", exception.getMessage());
    }
    
    /**
     * 测试查询所有用户功能
     */
    @Test
    public void testFindAllUsers() {
        // 清理测试数据（如果有）
        // 在实际项目中，通常会使用@Sql来准备和清理测试数据
        
        // 准备测试数据
        String phone1 = "13212345678";
        String phone2 = "13112345678";
        String nickname1 = "查询所有用户测试1";
        String nickname2 = "查询所有用户测试2";
        String password = "123456";

        // 注册两个用户
        userService.register(phone1, nickname1, password);
        userService.register(phone2, nickname2, password);

        // 执行查询所有用户操作
        java.util.List<User> users = userService.findAllUsers();

        // 验证结果
        assertNotNull(users);
        assertTrue(users.size() >= 2); // 至少有这两个用户
        
        // 验证是否包含我们刚创建的用户
        boolean containsUser1 = users.stream()
                .anyMatch(u -> phone1.equals(u.getPhone()));
        boolean containsUser2 = users.stream()
                .anyMatch(u -> phone2.equals(u.getPhone()));
        
        assertTrue(containsUser1, "应该包含第一个用户");
        assertTrue(containsUser2, "应该包含第二个用户");
    }

    /**
     * 测试根据ID查询用户功能
     */
    @Test
    public void testFindUserById() {
        // 准备测试数据
        String phone = "13012345678";
        String nickname = "ID查询测试用户";
        String password = "123456";

        // 注册用户
        User createdUser = userService.register(phone, nickname, password);

        // 执行根据ID查询操作
        User foundUser = userService.findUserById(createdUser.getId());

        // 验证结果
        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals(phone, foundUser.getPhone());
        assertEquals(nickname, foundUser.getNickname());
    }

    /**
     * 测试根据手机号查询用户功能
     */
    @Test
    public void testFindUserByPhone() {
        // 准备测试数据
        String phone = "14912345678";
        String nickname = "手机号查询测试用户";
        String password = "123456";

        // 注册用户
        userService.register(phone, nickname, password);

        // 执行根据手机号查询操作
        User foundUser = userService.findUserByPhone(phone);

        // 验证结果
        assertNotNull(foundUser);
        assertEquals(phone, foundUser.getPhone());
        assertEquals(nickname, foundUser.getNickname());
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
        // 注意：在实际更新中，密码字段可能会特殊处理，这里保持简单

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

    /**
     * 测试更新用户时手机号与其他用户冲突的情况
     */
    @Test
    public void testUpdateUserWithConflictingPhone() {
        // 准备测试数据
        String phone1 = "14612345678";
        String phone2 = "14512345678";
        String nickname1 = "用户1";
        String nickname2 = "用户2";
        String password = "123456";

        // 注册两个用户
        User user1 = userService.register(phone1, nickname1, password);
        User user2 = userService.register(phone2, nickname2, password);

        // 尝试将user1的手机号改为user2的手机号，应该抛出异常
        user1.setPhone(phone2);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(user1);
        });

        assertEquals("手机号已被其他用户使用", exception.getMessage());
    }

    /**
     * 测试删除用户功能
     */
    @Test
    public void testDeleteUser() {
        // 准备测试数据
        String phone = "14412345678";
        String nickname = "删除测试用户";
        String password = "123456";

        // 注册用户
        User user = userService.register(phone, nickname, password);

        // 确认用户已创建
        User existingUser = userService.findUserById(user.getId());
        assertNotNull(existingUser);

        // 执行删除操作
        int result = userService.deleteUser(user.getId());

        // 验证删除结果
        assertEquals(1, result); // 应该成功删除一条记录
        
        // 验证用户确实被删除
        User deletedUser = userService.findUserById(user.getId());
        assertNull(deletedUser, "用户应该已被删除");
    }

    /**
     * 测试删除不存在的用户
     */
    @Test
    public void testDeleteNonExistingUser() {
        // 尝试删除一个不存在的用户ID，应该抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(999999L); // 假设这个ID不存在
        });

        assertEquals("用户不存在，无法删除", exception.getMessage());
    }
}