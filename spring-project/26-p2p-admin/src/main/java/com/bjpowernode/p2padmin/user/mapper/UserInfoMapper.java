package com.bjpowernode.p2padmin.user.mapper;

import com.bjpowernode.p2padmin.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectUserByUsername(String username);

    List<UserInfo> selectUserInfoAll();

    List<Integer> selectUserRoleByUserId(Integer userId);

    void deleteUserRoleByUserId(Integer userId);

    void insertUserRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}