package com.bjpowernode.p2padmin.user.service;

import com.bjpowernode.p2padmin.user.model.RoleInfo;

import java.util.List;
import java.util.Map;

/**
 * ClassName:RoleService
 * Package:com.bjpowernode.p2padmin.user.service
 * Description:
 *
 * @date:2018/10/29 9:58
 * @author:bjpowernode.com
 */
public interface RoleService {
    List<RoleInfo> getRolesAll();

    List<Map> initPermissionTreeByRoleId(Integer roleId);

    void distributionPermissionByRoleId(Integer roleId, Integer[] perId);
}
