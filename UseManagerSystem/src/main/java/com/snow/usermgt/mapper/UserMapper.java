package com.snow.usermgt.mapper;

import com.snow.usermgt.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 用户数据访问层接口
 * 
 * 定义对用户表的各种数据库操作方法
 * 使用MyBatis注解方式实现SQL映射
 */
@Mapper
public interface UserMapper {

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户对象，如果不存在则返回null
     */
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(@Param("phone") String phone);

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户对象，如果不存在则返回null
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Long id);

    /**
     * 插入新用户
     * 
     * @param user 用户对象
     * @return 受影响的行数
     */
    @Insert("INSERT INTO user(phone, nickname, password, create_time) VALUES(#{phone}, #{nickname}, #{password}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    @Select("SELECT * FROM user")
    List<User> findAll();

    /**
     * 根据ID更新用户信息
     * 
     * @param user 用户对象
     * @return 受影响的行数
     */
    @Update("UPDATE user SET phone=#{phone}, nickname=#{nickname}, password=#{password} WHERE id=#{id}")
    int update(User user);

    /**
     * 根据ID删除用户
     * 
     * @param id 用户ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

}