package com.bjpowernode.p2padmin.user.service;

import com.bjpowernode.p2padmin.user.model.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * ClassName:UserService
 * Package:com.bjpowernode.p2padmin.user.service
 * Description:
 *
 * @date:2018/10/27 9:54
 * @author:bjpowernode.com
 */
public interface UserService {
    Integer login(UserInfo userInfo);

    List<UserInfo> getUserAll();

    List<Map> intiRoleDataByUserId(Integer userId);

    void disRole(Integer userId, Integer[] roleIds);
}
