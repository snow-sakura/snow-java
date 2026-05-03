package com.bjpowernode.fastdfs.service;

import com.bjpowernode.fastdfs.model.Creditor;

import java.util.List;

/**
 * ClassName:CreditorService
 * Package:com.bjpowernode.fastdfs.service
 * Description:
 *
 * @date:2018/10/13 10:28
 * @author:bjpowernode.com
 */
public interface CreditorService {
    List<Creditor> selectAll();

    Creditor selectCreditorById(Integer id);

    void updateFile(Creditor creditor);

    void deleteFile(Integer id);
}
