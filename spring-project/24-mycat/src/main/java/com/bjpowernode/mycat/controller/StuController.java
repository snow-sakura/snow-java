package com.bjpowernode.mycat.controller;

import com.bjpowernode.mycat.mapper.StuMapper;
import com.bjpowernode.mycat.model.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:StuController
 * Package:com.bjpowernode.mycat.controller
 * Description:
 *
 * @date:2018/10/22 10:31
 * @author:bjpowernode.com
 */
@RestController
public class StuController {
    @Autowired
    private StuMapper stuMapper;
    @RequestMapping("/stu/insert")
    public String insert(){
        Stu stu =new Stu();
        stu.setName("张三");

        stuMapper.insert(stu);
        return "学生添加成功";
    }
}
