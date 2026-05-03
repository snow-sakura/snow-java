package com.bjpowernode.springboot.controller;

import com.bjpowernode.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * ClassName:TestController
 * Package:com.bjpowernode.springboot.controller
 * Description:
 *
 * @date:2018/10/10 10:47
 * @author:bjpowernode.com
 */
@Controller
public class TestController {
    @RequestMapping("/index")
    public String index(Model model, HttpSession session){
        model.addAttribute("username","张三");
        model.addAttribute("user",new User(1,"李四",24,2));


        List<User>list=new ArrayList<User>();
        Map<String,User>map=new HashMap<String,User>();
        Random random=new Random();
        for(int i=1;i<=5;i++){
            User user=new User(1+i,"王五"+i,25+i,random.nextInt(2)+1);
            list.add(user);
            map.put("name"+i,user);
        }
        model.addAttribute("userList",list);
        model.addAttribute("userMap",map);

        model.addAttribute("xingQi",15);
        session.setAttribute("userSession",new User(25,"赵六",30,1));

        model.addAttribute("nowDate",new Date());
        model.addAttribute("money",123456);
        return "index";
    }
}
