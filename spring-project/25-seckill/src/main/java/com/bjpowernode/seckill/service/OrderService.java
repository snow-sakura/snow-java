package com.bjpowernode.seckill.service;

import com.bjpowernode.seckill.commons.CommonsReturnObject;

/**
 * ClassName:OrderService
 * Package:com.bjpowernode.seckill.service
 * Description:
 *
 * @date:2018/10/26 11:34
 * @author:bjpowernode.com
 */
public interface OrderService {
    CommonsReturnObject getOrderResult(Integer uid, String randomName);
}
