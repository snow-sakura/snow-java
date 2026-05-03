package com.bjpowernode.fastdfs.mapper;

import com.bjpowernode.fastdfs.model.Creditor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CreditorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Creditor record);

    int insertSelective(Creditor record);

    Creditor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Creditor record);

    int updateByPrimaryKey(Creditor record);

    List<Creditor> selectAll();

    void deleteFileById(Integer id);
}