package com.snow.usermgt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.usermgt.entity.User;
import com.snow.usermgt.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 用户控制器单元测试
 * 
 * 测试用户Controller的各个API端点，包括增删改查功能
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
        objectMapper = new ObjectMapper();
    }

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

    /**
     * 测试用户注册时手机号已存在的异常情况
     */
    @Test
    public void testRegisterWithExistingPhone() throws Exception {
        // 准备测试数据
        String phone = "13812345678";
        String nickname = "重复注册用户";
        String password = "123456";

        // 模拟服务层抛出异常
        when(userService.register(eq(phone), eq(nickname), eq(password)))
                .thenThrow(new RuntimeException("手机号已存在: " + phone));

        // 执行POST请求并验证错误响应
        mockMvc.perform(post("/api/users/register")
                .param("phone", phone)
                .param("nickname", nickname)
                .param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("手机号已存在: " + phone));

        // 验证服务层方法被调用
        verify(userService).register(eq(phone), eq(nickname), eq(password));
    }

    /**
     * 测试用户登录功能
     */
    @Test
    public void testLogin() throws Exception {
        // 准备测试数据
        String phone = "13812345678";
        String password = "123456";
        
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setPhone(phone);
        mockUser.setNickname("登录测试用户");
        mockUser.setPassword("encrypted_password");
        mockUser.setCreateTime(LocalDateTime.now());

        // 模拟服务层返回
        when(userService.login(eq(phone), eq(password))).thenReturn(mockUser);

        // 执行POST请求
        mockMvc.perform(post("/api/users/login")
                .param("phone", phone)
                .param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("登录成功"))
                .andExpect(jsonPath("$.data.phone").value(phone))
                .andExpect(jsonPath("$.data.nickname").value("登录测试用户"));

        // 验证服务层方法被调用
        verify(userService).login(eq(phone), eq(password));
    }

    /**
     * 测试查询所有用户功能 - READ操作
     */
    @Test
    public void testGetAllUsers() throws Exception {
        // 准备测试数据
        User user1 = new User();
        user1.setId(1L);
        user1.setPhone("13812345678");
        user1.setNickname("用户1");
        user1.setCreateTime(LocalDateTime.now());

        User user2 = new User();
        user2.setId(2L);
        user2.setPhone("13912345678");
        user2.setNickname("用户2");
        user2.setCreateTime(LocalDateTime.now());

        List<User> userList = Arrays.asList(user1, user2);

        // 模拟服务层返回
        when(userService.findAllUsers()).thenReturn(userList);

        // 执行GET请求
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("查询成功"))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].phone").value("13812345678"))
                .andExpect(jsonPath("$.data[0].nickname").value("用户1"))
                .andExpect(jsonPath("$.data[1].phone").value("13912345678"))
                .andExpect(jsonPath("$.data[1].nickname").value("用户2"));

        // 验证服务层方法被调用
        verify(userService).findAllUsers();
    }

    /**
     * 测试根据ID查询用户功能 - READ操作
     */
    @Test
    public void testGetUserById() throws Exception {
        // 准备测试数据
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setPhone("13812345678");
        mockUser.setNickname("ID查询测试用户");
        mockUser.setCreateTime(LocalDateTime.now());

        // 模拟服务层返回
        when(userService.findUserById(eq(userId))).thenReturn(mockUser);

        // 执行GET请求
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("查询成功"))
                .andExpect(jsonPath("$.data.phone").value("13812345678"))
                .andExpect(jsonPath("$.data.nickname").value("ID查询测试用户"));

        // 验证服务层方法被调用
        verify(userService).findUserById(eq(userId));
    }

    /**
     * 测试根据ID查询不存在的用户
     */
    @Test
    public void testGetUserByIdNotFound() throws Exception {
        // 准备测试数据
        Long userId = 999L; // 假设这个ID不存在

        // 模拟服务层返回null
        when(userService.findUserById(eq(userId))).thenReturn(null);

        // 执行GET请求并验证404响应
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("用户不存在"));

        // 验证服务层方法被调用
        verify(userService).findUserById(eq(userId));
    }

    /**
     * 测试根据手机号查询用户功能 - READ操作
     */
    @Test
    public void testGetUserByPhone() throws Exception {
        // 准备测试数据
        String phone = "13812345678";
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setPhone(phone);
        mockUser.setNickname("手机号查询测试用户");
        mockUser.setCreateTime(LocalDateTime.now());

        // 模拟服务层返回
        when(userService.findUserByPhone(eq(phone))).thenReturn(mockUser);

        // 执行GET请求
        mockMvc.perform(get("/api/users/phone/{phone}", phone)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("查询成功"))
                .andExpect(jsonPath("$.data.phone").value(phone))
                .andExpect(jsonPath("$.data.nickname").value("手机号查询测试用户"));

        // 验证服务层方法被调用
        verify(userService).findUserByPhone(eq(phone));
    }

    /**
     * 测试更新用户信息功能 - UPDATE操作
     */
    @Test
    public void testUpdateUser() throws Exception {
        // 准备测试数据
        Long userId = 1L;
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPhone("13812345678");
        updateUser.setNickname("更新后昵称");
        updateUser.setPassword("new_password");

        // 模拟服务层返回
        when(userService.updateUser(any(User.class))).thenReturn(1);

        // 执行PUT请求
        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("更新成功"));

        // 验证服务层方法被调用
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).updateUser(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertEquals(userId, capturedUser.getId());
        assertEquals("13812345678", capturedUser.getPhone());
        assertEquals("更新后昵称", capturedUser.getNickname());
    }

    /**
     * 测试删除用户功能 - DELETE操作
     */
    @Test
    public void testDeleteUser() throws Exception {
        // 准备测试数据
        Long userId = 1L;

        // 模拟服务层返回
        when(userService.deleteUser(eq(userId))).thenReturn(1);

        // 执行DELETE请求
        mockMvc.perform(delete("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("删除成功"));

        // 验证服务层方法被调用
        verify(userService).deleteUser(eq(userId));
    }

    /**
     * 测试删除不存在的用户
     */
    @Test
    public void testDeleteUserNotFound() throws Exception {
        // 准备测试数据
        Long userId = 999L; // 假设这个ID不存在

        // 模拟服务层抛出异常
        when(userService.deleteUser(eq(userId)))
                .thenThrow(new RuntimeException("用户不存在，无法删除"));

        // 执行DELETE请求并验证错误响应
        mockMvc.perform(delete("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("删除失败: 用户不存在，无法删除"));

        // 验证服务层方法被调用
        verify(userService).deleteUser(eq(userId));
    }
}