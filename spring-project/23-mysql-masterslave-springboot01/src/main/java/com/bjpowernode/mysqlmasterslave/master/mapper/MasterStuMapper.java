package com.bjpowernode.mysqlmasterslave.master.mapper;

import com.bjpowernode.mysqlmasterslave.model.Stu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterStuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stu record);

    int insertSelective(Stu record);

    Stu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stu record);

    int updateByPrimaryKey(Stu record);
}