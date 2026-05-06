package com.snow.usermgt.service;

import com.snow.usermgt.entity.User;
import com.snow.usermgt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户业务逻辑服务类
 * 
 * 处理用户相关的业务逻辑，包括注册、登录、查询等操作
 * 使用MD5对密码进行加密存储
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * 
     * 创建新用户，对密码进行MD5加密
     * 
     * @param phone 手机号
     * @param nickname 昵称
     * @param password 原始密码
     * @return 注册成功的用户对象
     * @throws RuntimeException 如果手机号已存在
     */
    public User register(String phone, String nickname, String password) {
        // 检查手机号是否已存在
        User existingUser = userMapper.findByPhone(phone);
        if (existingUser != null) {
            throw new RuntimeException("手机号已存在: " + phone);
        }

        // 创建新用户对象
        User user = new User();
        user.setPhone(phone);
        user.setNickname(nickname);
        // 对密码进行MD5加密存储
        user.setPassword(md5Encrypt(password));
        user.setCreateTime(LocalDateTime.now());

        // 插入数据库
        userMapper.insert(user);
        return user;
    }

    /**
     * 用户登录
     * 
     * 验证用户手机号和密码是否匹配
     * 
     * @param phone 手机号
     * @param password 原始密码
     * @return 登录成功的用户对象
     * @throws RuntimeException 如果手机号不存在或密码错误
     */
    public User login(String phone, String password) {
        // 根据手机号查找用户
        User user = userMapper.findByPhone(phone);
        if (user == null) {
            throw new RuntimeException("手机号不存在: " + phone);
        }

        // 验证密码（将输入的密码进行MD5加密后与数据库中的密码比较）
        String encryptedPassword = md5Encrypt(password);
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }

    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    public List<User> findAllUsers() {
        return userMapper.findAll();
    }

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户对象，如果不存在则返回null
     */
    public User findUserById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户对象，如果不存在则返回null
     */
    public User findUserByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    /**
     * 更新用户信息
     * 
     * @param user 用户对象
     * @return 更新的记录数
     */
    public int updateUser(User user) {
        // 检查用户是否存在
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在，无法更新");
        }
        
        // 检查手机号是否与其他用户冲突
        User phoneUser = userMapper.findByPhone(user.getPhone());
        if (phoneUser != null && !phoneUser.getId().equals(user.getId())) {
            throw new RuntimeException("手机号已被其他用户使用");
        }
        
        // 对密码进行MD5加密（如果提供了新密码）
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(md5Encrypt(user.getPassword()));
        } else {
            // 如果没有提供新密码，则保留原密码
            user.setPassword(existingUser.getPassword());
        }
        
        return userMapper.update(user);
    }

    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 删除的记录数
     */
    public int deleteUser(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在，无法删除");
        }
        
        return userMapper.deleteById(id);
    }

    /**
     * MD5加密方法
     * 
     * @param input 要加密的字符串
     * @return 加密后的十六进制字符串
     */
    private String md5Encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }
}