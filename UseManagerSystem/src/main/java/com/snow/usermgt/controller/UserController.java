package com.snow.usermgt.controller;

import com.snow.usermgt.entity.User;
import com.snow.usermgt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * 
 * 提供用户相关的REST API接口，包括注册、登录和查询功能
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户管理相关的API接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * 
     * @param phone 手机号
     * @param nickname 昵称
     * @param password 密码
     * @return 注册结果
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册新用户")
    public ResponseEntity<Map<String, Object>> register(
            @RequestParam 
            @NotBlank(message = "手机号不能为空") 
            @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确") 
            @Parameter(description = "手机号", required = true) String phone,
            @RequestParam 
            @NotBlank(message = "昵称不能为空") 
            @Parameter(description = "昵称", required = true) String nickname,
            @RequestParam 
            @NotBlank(message = "密码不能为空") 
            @Parameter(description = "密码", required = true) String password) {
        
        try {
            // 调用服务层进行用户注册
            User user = userService.register(phone, nickname, password);
            
            // 返回成功响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("data", user);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // 处理业务异常
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 用户登录接口
     * 
     * @param phone 手机号
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录验证")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam 
            @NotBlank(message = "手机号不能为空") 
            @Parameter(description = "手机号", required = true) String phone,
            @RequestParam 
            @NotBlank(message = "密码不能为空") 
            @Parameter(description = "密码", required = true) String password) {
        
        try {
            // 调用服务层进行用户登录验证
            User user = userService.login(phone, password);
            
            // 返回成功响应，不包含密码信息
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "登录成功");
            // 创建不包含密码的用户信息返回
            User userInfo = new User();
            userInfo.setId(user.getId());
            userInfo.setPhone(user.getPhone());
            userInfo.setNickname(user.getNickname());
            userInfo.setCreateTime(user.getCreateTime());
            response.put("data", userInfo);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // 处理业务异常
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 查询所有用户接口
     * 
     * @return 用户列表
     */
    @GetMapping
    @Operation(summary = "查询所有用户", description = "获取所有用户信息")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            // 调用服务层查询所有用户
            List<User> users = userService.findAllUsers();
            
            // 返回成功响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "查询成功");
            response.put("data", users);
            response.put("count", users.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 处理异常
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 根据ID查询用户接口
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户", description = "根据用户ID获取用户详细信息")
    public ResponseEntity<Map<String, Object>> getUserById(
            @PathVariable 
            @Min(value = 1, message = "用户ID必须大于0") 
            @Parameter(description = "用户ID", required = true) Long id) {
        try {
            // 调用服务层根据ID查询用户
            User user = userService.findUserById(id);
            
            if (user != null) {
                // 用户存在，返回用户信息
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "查询成功");
                // 创建不包含密码的用户信息返回
                User userInfo = new User();
                userInfo.setId(user.getId());
                userInfo.setPhone(user.getPhone());
                userInfo.setNickname(user.getNickname());
                userInfo.setCreateTime(user.getCreateTime());
                response.put("data", userInfo);
                
                return ResponseEntity.ok(response);
            } else {
                // 用户不存在
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "用户不存在");
                
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 根据手机号查询用户接口
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/phone/{phone}")
    @Operation(summary = "根据手机号查询用户", description = "根据手机号获取用户详细信息")
    public ResponseEntity<Map<String, Object>> getUserByPhone(
            @PathVariable 
            @Parameter(description = "手机号", required = true) String phone) {
        try {
            // 调用服务层根据手机号查询用户
            User user = userService.findUserByPhone(phone);
            
            if (user != null) {
                // 用户存在，返回用户信息
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "查询成功");
                // 创建不包含密码的用户信息返回
                User userInfo = new User();
                userInfo.setId(user.getId());
                userInfo.setPhone(user.getPhone());
                userInfo.setNickname(user.getNickname());
                userInfo.setCreateTime(user.getCreateTime());
                response.put("data", userInfo);
                
                return ResponseEntity.ok(response);
            } else {
                // 用户不存在
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "用户不存在");
                
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 处理异常
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 更新用户信息接口
     * 
     * @param id 用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户信息", description = "根据用户ID更新用户信息")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable 
            @Min(value = 1, message = "用户ID必须大于0") 
            @Parameter(description = "用户ID", required = true) Long id,
            @RequestBody @Valid User user) {
        try {
            // 设置ID，防止客户端修改
            user.setId(id);
            // 调用服务层更新用户
            int result = userService.updateUser(user);
            
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "更新成功");
                response.put("data", user);
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "更新失败");
                
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 删除用户接口
     * 
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable 
            @Min(value = 1, message = "用户ID必须大于0") 
            @Parameter(description = "用户ID", required = true) Long id) {
        try {
            // 调用服务层删除用户
            int result = userService.deleteUser(id);
            
            if (result > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "删除成功");
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "删除失败");
                
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "删除失败: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
}