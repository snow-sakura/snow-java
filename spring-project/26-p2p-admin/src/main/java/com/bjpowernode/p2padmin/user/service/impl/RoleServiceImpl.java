package com.bjpowernode.p2padmin.user.service.impl;

import com.bjpowernode.p2padmin.user.mapper.PermissionInfoMapper;
import com.bjpowernode.p2padmin.user.mapper.RoleInfoMapper;
import com.bjpowernode.p2padmin.user.model.PermissionInfo;
import com.bjpowernode.p2padmin.user.model.RoleInfo;
import com.bjpowernode.p2padmin.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:RoleServiceImpl
 * Package:com.bjpowernode.p2padmin.user.service.impl
 * Description:
 *
 * @date:2018/10/29 9:59
 * @author:bjpowernode.com
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;
    @Override
    public List<RoleInfo> getRolesAll() {
        return roleInfoMapper.selectAll();
    }

    @Override
    public List<Map> initPermissionTreeByRoleId(Integer roleId) {


        List<Map> permissionTreeData=new ArrayList<Map>();

        List<PermissionInfo>permissionInfoList= permissionInfoMapper.selectAll();
        List<PermissionInfo>rolePermissionList= permissionInfoMapper.selectAllByRoleId(roleId);
        Map rolePermissionMap=new HashMap();
        for(PermissionInfo per:rolePermissionList){//使用当前角色下所有的权限id作为key使用任意非null的数据作为value写入Map集合
            rolePermissionMap.put(per.getId(),"");
        }

        //        [
//        { id:1, pId:0, name:"随意勾选 1", open:true},
//        { id:11, pId:1, name:"随意勾选3333 1-1", open:true},
//        { id:111, pId:11, name:"随意勾选 1-1-1"},
//        { id:112, pId:11, name:"随意勾选 1-1-2"},
//        { id:12, pId:1, name:"随意勾选 1-2", open:true},
//        { id:121, pId:12, name:"随意勾选 1-2-1"},
//        { id:122, pId:12, name:"随意勾选 1-2-2"},
//        { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
//        { id:21, pId:2, name:"随意勾选 2-1"},
//        { id:22, pId:2, name:"随意勾选 2-2", open:true},
//        { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
//        { id:222, pId:22, name:"随意勾选 2-2-2"},
//        { id:23, pId:2, name:"随意勾选 2-3"}
//        ]

        for(PermissionInfo per: permissionInfoList){
            Map treeNode=new HashMap();
            treeNode.put("id",per.getId());
            treeNode.put("pId",per.getParentId()== null?0:per.getParentId());
            treeNode.put("name",per.getName());
            if(rolePermissionMap.get(per.getId())!=null){
              treeNode.put("checked",true);
            }
            permissionTreeData.add(treeNode);
        }

        return permissionTreeData;
    }

    @Override
    public void distributionPermissionByRoleId(Integer roleId, Integer[] perId) {
        roleInfoMapper.deletePermissionByRoleId(roleId);
        if(perId!=null){
            for(Integer pid:perId){
                roleInfoMapper.insertRolePermission(roleId,pid);
            }


        }
    }
}
