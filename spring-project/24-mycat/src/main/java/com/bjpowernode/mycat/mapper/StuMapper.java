package com.bjpowernode.mycat.mapper;

import com.bjpowernode.mycat.model.Stu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stu record);

    int insertSelective(Stu record);

    Stu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stu record);

    int updateByPrimaryKey(Stu record);
}