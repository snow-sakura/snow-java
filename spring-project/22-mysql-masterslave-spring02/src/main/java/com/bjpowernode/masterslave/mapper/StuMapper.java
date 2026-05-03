package com.bjpowernode.masterslave.mapper;

import com.bjpowernode.masterslave.model.Stu;

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