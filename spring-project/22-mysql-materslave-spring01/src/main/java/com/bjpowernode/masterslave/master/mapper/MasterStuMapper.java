package com.bjpowernode.masterslave.master.mapper;

import com.bjpowernode.masterslave.model.Stu;

public interface MasterStuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stu record);

    int insertSelective(Stu record);

    Stu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stu record);

    int updateByPrimaryKey(Stu record);
}