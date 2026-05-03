package com.bjpowernode.fastdfs.service.impl;

import com.bjpowernode.fastdfs.mapper.CreditorMapper;
import com.bjpowernode.fastdfs.model.Creditor;
import com.bjpowernode.fastdfs.service.CreditorService;
import com.bjpowernode.fastdfs.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:CreditorServiceImpl
 * Package:com.bjpowernode.fastdfs.service.impl
 * Description:
 *
 * @date:2018/10/13 10:29
 * @author:bjpowernode.com
 */
@Service("creditorService")
public class CreditorServiceImpl implements CreditorService {
    @Autowired
    private CreditorMapper creditorMapper;
    @Override
    public List<Creditor> selectAll() {

        return creditorMapper.selectAll();
    }

    @Override
    public Creditor selectCreditorById(Integer id) {
        return creditorMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateFile(Creditor creditor) {
        creditorMapper.updateByPrimaryKeySelective(creditor);
    }

    @Override
    public void deleteFile(Integer id) {
        Creditor creditor= creditorMapper.selectByPrimaryKey(id);
        FastDFSUtil.delete(creditor.getGroupName(),creditor.getRemoteFileName());


        creditorMapper.deleteFileById(id);

    }
}
