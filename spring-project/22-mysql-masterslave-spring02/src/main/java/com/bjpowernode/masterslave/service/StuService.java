package com.bjpowernode.masterslave.service;

import com.bjpowernode.masterslave.config.DynamicDataSource;
import com.bjpowernode.masterslave.config.ThreadLocalDataSource;
import com.bjpowernode.masterslave.mapper.StuMapper;
import com.bjpowernode.masterslave.model.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ClassName:StuService
 * Package:com.bjpowernode.masterslave.service
 * Description:
 *
 * @date:2018/10/20 9:22
 * @author:bjpowernode.com
 */
@Service("stuService")
public class StuService {
    @Autowired
    private StuMapper stuMapper;

    public void addStu(){
        Stu stu=new Stu();
        stu.setName("李四");
        ThreadLocalDataSource.setDataSource(DynamicDataSource.MASTER);
        stuMapper.insert(stu);
    }

    public void select(){
        ThreadLocalDataSource.setDataSource(DynamicDataSource.SLAVE);
       List<Stu>list= stuMapper.selectAll();
       list.forEach(stu-> System.out.println("id="+stu.getId()+"    name="+stu.getName()));
    }
}
