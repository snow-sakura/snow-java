package com.bjpowernode.seckill.listener;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.mapper.GoodsMapper;
import com.bjpowernode.seckill.mapper.OrderMapper;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.model.Order;
import com.bjpowernode.seckill.service.GoodsService;
import com.bjpowernode.seckill.service.OrderService;
import com.bjpowernode.seckill.utils.JedisPoolInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:SecKillMQListener
 * Package:com.bjpowernode.seckill.listener
 * Description:
 *
 * @date:2018/10/26 11:35
 * @author:bjpowernode.com
 */
@Component
public class SecKillMQListener {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @JmsListener(destination = "secKill")
    public void onMessage(String orderData){
        Order order= JSONObject.parseObject(orderData,Order.class);
        Goods goods= goodsMapper.selectByPrimaryKey(order.getGoodsId());
        order.setBuyPrice(goods.getPrice());
        order.setOrderMoney(goods.getPrice().multiply(new BigDecimal(order.getBuyNum())));
        order.setStatus(1);//表示待支付
        order.setCreateTime(new Date());
        Jedis jedis= JedisPoolInstance.getJedisPoolInstance().getResource();
        jedis.lpop(CommonsConstants.LIMITING_LIST);
        orderMapper.insert(order);
        jedis.set(CommonsConstants.ORDER_RESULT+goods.getRandomName()+order.getUid(),JSONObject.toJSONString(order));
    }
}
