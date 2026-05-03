package com.bjpowernode.mysqlmasterslave.mapper;

import com.bjpowernode.mysqlmasterslave.model.Stu;

import java.util.List;

public interface StuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stu record);

    int insertSelective(Stu record);

    Stu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stu record);

    int updateByPrimaryKey(Stu record);

    List<Stu> selectAll();
}