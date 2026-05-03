package com.bjpowernode.p2padmin.creditor.mapper;

import com.bjpowernode.p2padmin.creditor.model.CreditorRights;

public interface CreditorRightsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreditorRights record);

    int insertSelective(CreditorRights record);

    CreditorRights selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreditorRights record);

    int updateByPrimaryKey(CreditorRights record);
}