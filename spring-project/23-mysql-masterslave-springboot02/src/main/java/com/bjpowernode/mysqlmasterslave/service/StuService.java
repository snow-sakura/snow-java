package com.bjpowernode.mysqlmasterslave.service;

import com.bjpowernode.mysqlmasterslave.config.DynamicDataSource;
import com.bjpowernode.mysqlmasterslave.config.ThreadLocalDataSource;
import com.bjpowernode.mysqlmasterslave.mapper.StuMapper;
import com.bjpowernode.mysqlmasterslave.model.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:StuService
 * Package:com.bjpowernode.mysqlmasterslave.service
 * Description:
 *
 * @date:2018/10/20 10:54
 * @author:bjpowernode.com
 */
@Service("stuService")
public class StuService {
    @Autowired
    private StuMapper stuMapper;

    public void addStu(){
        Stu stu=new Stu();
        stu.setName("张飞");

        ThreadLocalDataSource.setDataSource(DynamicDataSource.MASTER);
        stuMapper.insert(stu);
    }
    public void select(){
        ThreadLocalDataSource.setDataSource(DynamicDataSource.SLAVE);
        List<Stu>list=stuMapper.selectAll();
        list.forEach(stu-> System.out.println("id="+stu.getId()+"   name="+stu.getName()));
    }
}
