package com.bjpowernode.p2padmin.user.mapper;

import com.bjpowernode.p2padmin.user.model.RoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> selectAll();

    void deletePermissionByRoleId(Integer roleId);

    void insertRolePermission(@Param("roleId") Integer roleId,@Param("permissionId") Integer pid);
}