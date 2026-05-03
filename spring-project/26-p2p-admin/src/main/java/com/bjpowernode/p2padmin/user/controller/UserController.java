package com.bjpowernode.p2padmin.user.controller;

import com.bjpowernode.p2padmin.user.model.UserInfo;
import com.bjpowernode.p2padmin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.p2padmin.user.controller
 * Description:
 *
 * @date:2018/10/27 9:23
 * @author:bjpowernode.com
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String toLogin(HttpServletRequest request,HttpServletResponse response, HttpSession session,Model model){
        Cookie cookies[]=request.getCookies();
        if(cookies==null){
            return "user/login";
        }
        UserInfo userInfo=new UserInfo();
        for(Cookie cookie: cookies){
            if("username".equals(cookie.getName())){
                userInfo.setUsername(cookie.getValue());
                continue;
            }
            if("password".equals(cookie.getName())){
                userInfo.setPassword(cookie.getValue());
                continue;
            }
        }
        if(userInfo.getUsername()==null||userInfo.getPassword()==null){
            return "user/login";
        }

        Integer result= userService.login(userInfo);
        switch (result){
            case 1:
                model.addAttribute("errorMessage","对不起！账号错误！");
                return "user/login";
            case 2:
                model.addAttribute("errorMessage","对不起！密码错误！");
                Cookie cookieUsername1=new Cookie("username","");
                cookieUsername1.setMaxAge(1);
                cookieUsername1.setPath("/");
                Cookie cookiePassword1=new Cookie("password", "");
                cookiePassword1.setMaxAge(1);
                cookiePassword1.setPath("/");
                response.addCookie(cookieUsername1);
                response.addCookie(cookiePassword1);
                return "user/login";
        }

            session.setAttribute("userSession",userInfo);
            Cookie cookieUsername=new Cookie("username",userInfo.getUsername());
            cookieUsername.setMaxAge(60*60*24*7);
            cookieUsername.setPath("/");
            Cookie cookiePassword=new Cookie("password", userInfo.getPassword());
            cookiePassword.setMaxAge(60*60*24*7);
            cookiePassword.setPath("/");
            response.addCookie(cookieUsername);
            response.addCookie(cookiePassword);

        return "main";
    }


    @RequestMapping("/login")
    public String login(UserInfo userInfo, String isAutoLogin, Model model, HttpSession session, HttpServletResponse response){
        Integer result= userService.login(userInfo);

        switch (result){
            case 1:
                model.addAttribute("errorMessage","对不起！账号错误！");
                return "user/login";
            case 2:
                model.addAttribute("errorMessage","对不起！密码错误！");
                return "user/login";
        }

        session.setAttribute("userSession",userInfo);

        if(isAutoLogin!=null){
            Cookie cookieUsername=new Cookie("username",userInfo.getUsername());
            cookieUsername.setMaxAge(60*60*24*7);
            cookieUsername.setPath("/");
            Cookie cookiePassword=new Cookie("password", userInfo.getPassword());
            cookiePassword.setMaxAge(60*60*24*7);
            cookiePassword.setPath("/");
            response.addCookie(cookieUsername);
            response.addCookie(cookiePassword);
        }

        return "main";
    }
    @RequestMapping("/onPermission")
    public String onPermission(){
        return "user/onPermission";
    }


    @RequestMapping("/admin/users")
    public String users( Model model){
        List<UserInfo> userList=userService.getUserAll();
        model.addAttribute("userInfoList",userList);

        return "user/users";
    }
    @RequestMapping("/toDisRole/{userId}")
    public String toDisRole(@PathVariable Integer userId ,Model model){
        List<Map> roleData= userService.intiRoleDataByUserId(userId);
        model.addAttribute("roleDataList",roleData);
        model.addAttribute("userId",userId);
        return "user/distributionRole";
    }
    @RequestMapping("/admin/disRole")
    public String disRole(Integer userId,Integer [] roleIds){

        userService.disRole(userId,roleIds);

        return "redirect:/admin/users";
    }

}
