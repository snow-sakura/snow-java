package com.bjpowernode.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName:UserController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/11 11:45
 * @author:bjpowernode.com
 */
@Controller
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        List<User>list= userService.selectAll();
        model.addAttribute("userList",list);
        return "index";
    }
    @GetMapping("/goUpdate/{id}")
    public String goUpdate(@PathVariable Integer id,Model model){
        User user=userService.selectUserById(id);
        model.addAttribute("user",user);
        return "user";
    }

    @PutMapping("/update")
    public String update(User user){
        userService.updateUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userService.delete(id);
        return "redirect:/";
    }
}
