package com.snow.usermgt.mapper;

import com.snow.usermgt.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户数据访问层单元测试
 * 
 * 测试用户Mapper的增删改查功能
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

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

    /**
     * 测试根据ID查找用户功能 - READ操作
     */
    @Test
    public void testFindById() {
        // 准备测试数据
        User user = new User();
        user.setPhone("13912345678");
        user.setNickname("查找测试用户");
        user.setPassword("encrypted_password");
        user.setCreateTime(LocalDateTime.now());

        // 先插入用户
        userMapper.insert(user);

        // 执行查找操作
        User foundUser = userMapper.findById(user.getId());

        // 验证结果
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals("13912345678", foundUser.getPhone());
        assertEquals("查找测试用户", foundUser.getNickname());
    }

    /**
     * 测试根据手机号查找用户功能 - READ操作
     */
    @Test
    public void testFindByPhone() {
        // 准备测试数据
        User user = new User();
        user.setPhone("13712345678");
        user.setNickname("手机号查找测试用户");
        user.setPassword("encrypted_password");
        user.setCreateTime(LocalDateTime.now());

        // 先插入用户
        userMapper.insert(user);

        // 执行查找操作
        User foundUser = userMapper.findByPhone("13712345678");

        // 验证结果
        assertNotNull(foundUser);
        assertEquals("13712345678", foundUser.getPhone());
        assertEquals("手机号查找测试用户", foundUser.getNickname());
    }

    /**
     * 测试查找所有用户功能 - READ操作
     */
    @Test
    public void testFindAll() {
        // 清空表中的数据以确保测试准确性
        // 注意：实际项目中可能需要更复杂的清理机制
        
        // 准备测试数据
        User user1 = new User();
        user1.setPhone("13612345678");
        user1.setNickname("所有用户测试1");
        user1.setPassword("encrypted_password");
        user1.setCreateTime(LocalDateTime.now());

        User user2 = new User();
        user2.setPhone("13512345678");
        user2.setNickname("所有用户测试2");
        user2.setPassword("encrypted_password");
        user2.setCreateTime(LocalDateTime.now());

        // 插入两个用户
        userMapper.insert(user1);
        userMapper.insert(user2);

        // 执行查找所有操作
        List<User> allUsers = userMapper.findAll();

        // 验证结果
        assertNotNull(allUsers);
        assertTrue(allUsers.size() >= 2); // 至少有这两个用户
        
        // 验证是否包含我们插入的用户
        boolean containsUser1 = allUsers.stream()
                .anyMatch(u -> u.getPhone().equals("13612345678"));
        boolean containsUser2 = allUsers.stream()
                .anyMatch(u -> u.getPhone().equals("13512345678"));
        
        assertTrue(containsUser1, "应该包含第一个用户");
        assertTrue(containsUser2, "应该包含第二个用户");
    }

    /**
     * 测试更新用户功能 - UPDATE操作
     */
    @Test
    public void testUpdateUser() {
        // 准备测试数据
        User user = new User();
        user.setPhone("13412345678");
        user.setNickname("原始昵称");
        user.setPassword("original_password");
        user.setCreateTime(LocalDateTime.now());

        // 先插入用户
        userMapper.insert(user);

        // 修改用户信息
        user.setNickname("更新后昵称");
        user.setPassword("updated_password");

        // 执行更新操作
        int result = userMapper.update(user);

        // 验证结果
        assertEquals(1, result); // 确认有一条记录被更新
        
        // 从数据库重新查询验证更新是否生效
        User updatedUser = userMapper.findById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("更新后昵称", updatedUser.getNickname());
        assertEquals("updated_password", updatedUser.getPassword());
    }

    /**
     * 测试删除用户功能 - DELETE操作
     */
    @Test
    public void testDeleteById() {
        // 准备测试数据
        User user = new User();
        user.setPhone("13312345678");
        user.setNickname("删除测试用户");
        user.setPassword("encrypted_password");
        user.setCreateTime(LocalDateTime.now());

        // 先插入用户
        userMapper.insert(user);

        // 确认用户已存在
        User existingUser = userMapper.findById(user.getId());
        assertNotNull(existingUser);

        // 执行删除操作
        int result = userMapper.deleteById(user.getId());

        // 验证结果
        assertEquals(1, result); // 确认有一条记录被删除
        
        // 验证用户确实被删除
        User deletedUser = userMapper.findById(user.getId());
        assertNull(deletedUser, "用户应该已被删除");
    }
}