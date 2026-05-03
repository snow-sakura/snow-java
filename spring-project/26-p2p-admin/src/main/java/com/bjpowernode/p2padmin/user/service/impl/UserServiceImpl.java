package com.bjpowernode.p2padmin.user.service.impl;

import com.bjpowernode.p2padmin.user.mapper.PermissionInfoMapper;
import com.bjpowernode.p2padmin.user.mapper.RoleInfoMapper;
import com.bjpowernode.p2padmin.user.mapper.UserInfoMapper;
import com.bjpowernode.p2padmin.user.model.PermissionInfo;
import com.bjpowernode.p2padmin.user.model.RoleInfo;
import com.bjpowernode.p2padmin.user.model.UserInfo;
import com.bjpowernode.p2padmin.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName:UserServiceImpl
 * Package:com.bjpowernode.p2padmin.user.service.impl
 * Description:
 *
 * @date:2018/10/27 9:54
 * @author:bjpowernode.com
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;
    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Override
    public Integer login(UserInfo userInfo) {
        UserInfo tempUser = userInfoMapper.selectUserByUsername(userInfo.getUsername());

        if(tempUser==null){
            return 1;
        }
        if(!tempUser.getPassword().equals(userInfo.getPassword())){
            return 2;
        }
        BeanUtils.copyProperties(tempUser,userInfo);
        userInfo.setLastLoginTime(new Date());
        userInfo.setLoginCount(userInfo.getLoginCount()==null?1:userInfo.getLoginCount()+1);
        userInfoMapper.updateByPrimaryKey(userInfo);
        //获取当前用户id下所有的菜单权限
        List<PermissionInfo>menuList= permissionInfoMapper.selectMenuPermissionByUserId(userInfo.getId(),1);
        userInfo.setMenuList(menuList);
        List<String>urlPermissionList= permissionInfoMapper.selectUrlPermissionByUserId(userInfo.getId());
        Map userUrlPermissionMap= new HashMap();
        for(String str:urlPermissionList){//将用户所对应的url权限存入Map集合使用url为key任意非null的值作为value即可
            userUrlPermissionMap.put(str,"");
        }
        userInfo.setUrlMap(userUrlPermissionMap);

        return 0;
    }

    @Override
    public List<UserInfo> getUserAll() {
        List<UserInfo> list=userInfoMapper.selectUserInfoAll();
        return list;
    }

    @Override
    public List<Map> intiRoleDataByUserId(Integer userId) {
        List<RoleInfo> roleInfoList= roleInfoMapper.selectAll();
        List<Integer> userRoleIds=  userInfoMapper.selectUserRoleByUserId(userId);
        List<Map> list=new ArrayList<Map>();
        for(RoleInfo roleInfo:roleInfoList){
            Map map=new HashMap();
            map.put("roleId",roleInfo.getId());
            map.put("roleName",roleInfo.getName());
            if(userRoleIds.indexOf(roleInfo.getId())>=0){
                map.put("selected",true);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public void disRole(Integer userId, Integer[] roleIds) {
        userInfoMapper.deleteUserRoleByUserId(userId);
        if(roleIds!=null){
            for(Integer roleId:roleIds){
                userInfoMapper.insertUserRole(userId,roleId);
            }

        }

    }
}
