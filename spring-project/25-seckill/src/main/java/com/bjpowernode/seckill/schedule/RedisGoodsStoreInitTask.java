package com.bjpowernode.seckill.schedule;


import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.service.GoodsService;
import com.bjpowernode.seckill.utils.JedisPoolInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.Jedis;

import java.util.List;
/**
 * ClassName:RedisGoodsStoreInitTask
 * Package:com.bjpowernode.seckill.schedule
 * Description:
 *
 * @date:2018/10/25 11:17
 * @author:bjpowernode.com
 */

@Configuration
@EnableScheduling
public class RedisGoodsStoreInitTask {

    @Autowired
    private GoodsService goodsService;

    /**
     * 5秒执行一次定时任务
     * 在redis中初始化商品库存
     *
     */
    @Scheduled(cron="0/5 * * * * *")
    public void initGoodsStore() {
        //查询一下秒杀的商品
        List<Goods> goodsList = goodsService.getAllGoods();

        Jedis jedis = JedisPoolInstance.getJedisPoolInstance().getResource();
        for (Goods goods : goodsList) {
            //操作redis，将商品库存存入redis
            jedis.setnx(CommonsConstants.SECKILL_STORE + goods.getRandomName(), String.valueOf(goods.getStore()));
        }
        jedis.close();
    }
}