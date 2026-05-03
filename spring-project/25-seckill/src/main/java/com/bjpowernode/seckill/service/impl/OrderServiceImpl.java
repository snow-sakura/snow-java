package com.bjpowernode.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.model.Order;
import com.bjpowernode.seckill.service.OrderService;
import com.bjpowernode.seckill.utils.JedisPoolInstance;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * ClassName:OrderServiceImpl
 * Package:com.bjpowernode.seckill.service.impl
 * Description:
 *
 * @date:2018/10/26 11:34
 * @author:bjpowernode.com
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public CommonsReturnObject getOrderResult(Integer uid, String randomName) {
        CommonsReturnObject returnObject=new CommonsReturnObject();
        Jedis jedis= JedisPoolInstance.getJedisPoolInstance().getResource();
        String strOrder= jedis.get(CommonsConstants.ORDER_RESULT+randomName+uid);
        if(strOrder==null||"".equals(strOrder)){
            returnObject.setErrorCode(CommonsConstants.ONE);
            returnObject.setErrorMessage("");
            returnObject.setData(null);
            return returnObject;
        }
        returnObject.setErrorCode(CommonsConstants.ZERO);
        returnObject.setErrorMessage("");
        returnObject.setData(JSONObject.parseObject(strOrder, Order.class));
        return returnObject;
    }
}
