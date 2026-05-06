package com.snow.usermgt.entity;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * 该类映射数据库中的用户表，包含用户的基本信息
 */
public class User {
    
    /**
     * 用户ID，主键
     */
    private Long id;
    
    /**
     * 手机号，用户的唯一标识
     */
    private String phone;
    
    /**
     * 昵称，用户显示名称
     */
    private String nickname;
    
    /**
     * 密码，加密后的密码
     */
    private String password;
    
    /**
     * 注册时间，用户创建的时间戳
     */
    private LocalDateTime createTime;
    
    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}