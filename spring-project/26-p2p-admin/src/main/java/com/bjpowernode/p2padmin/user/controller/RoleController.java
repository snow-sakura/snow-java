package com.bjpowernode.p2padmin.user.controller;

import com.bjpowernode.p2padmin.user.model.RoleInfo;
import com.bjpowernode.p2padmin.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ClassName:RoleController
 * Package:com.bjpowernode.p2padmin.user.controller
 * Description:
 *
 * @date:2018/10/29 9:48
 * @author:bjpowernode.com
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/admin/roles")
    public String roles(Model model){
       List<RoleInfo>list= roleService.getRolesAll();

        model.addAttribute("rolesList",list);
        return "user/roles";
    }

    @RequestMapping("/toDisPermission/{roleId}")
    public String toDisPermission(@PathVariable Integer roleId ,Model model){
        model.addAttribute("roleId",roleId);
        return "user/distributionPermission";
    }
    @RequestMapping("/initPermissionTree/{roleId}")
    public @ResponseBody
    Object initPermissionTree(@PathVariable Integer roleId){
        System.out.println(roleId);
        List<Map> permissionTreeData= roleService.initPermissionTreeByRoleId(roleId);
        return permissionTreeData;
    }
    @RequestMapping("/disPermission")
    public @ResponseBody String disPermission(Integer [] perId,Integer roleId){
        roleService.distributionPermissionByRoleId(roleId,perId);
        return "ok";
    }
}
