package com.bjpowernode.p2padmin.user.mapper;

import com.bjpowernode.p2padmin.user.model.PermissionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    PermissionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);

    List<PermissionInfo> selectMenuPermissionByUserId(@Param("userId") Integer userId,@Param("parentId") Integer parentId);

    List<String> selectUrlPermissionByUserId(Integer id);

    List<PermissionInfo> selectAll();

    List<PermissionInfo> selectAllByRoleId(Integer roleId);
}