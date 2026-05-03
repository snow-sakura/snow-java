package com.bjpowernode.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.mapper.GoodsMapper;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.model.Order;
import com.bjpowernode.seckill.service.GoodsService;
import com.bjpowernode.seckill.utils.JedisPoolInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * ClassName:GoodsServiceImpl
 * Package:com.bjpowernode.seckill.service.impl
 * Description:
 *
 * @date:2018/10/25 11:19
 * @author:bjpowernode.com
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.selectAll();
    }

    @Override
    public Goods getGoodsByGoodsId(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public CommonsReturnObject secKill(Integer uid, Integer goodsId, String randomName) {
        CommonsReturnObject returnObject=new CommonsReturnObject();
        Jedis jedis=null;
        //获取Jedis对象用于操作Redis
        jedis = JedisPoolInstance.getJedisPoolInstance().getResource();
        //根据商品的随机名获取秒杀商品库存判断是否还有商品如果没有剩余则直接返回给与提示
        String strStore= jedis.get(CommonsConstants.SECKILL_STORE+randomName);
        int store=0;
        try {
            store=Integer.parseInt(strStore);
        } catch (Exception e) {

        }
//        if(store<=0){
//            returnObject.setErrorCode(CommonsConstants.ONE);
//            returnObject.setErrorMessage("对不起!商品已被抢光！");
//            return returnObject;
//        }
//        //根据商品随机名和用户id从redis中获取订单信息，如果有了则表示已经秒杀过商品并给与提示
//        String handleUser=  jedis.get(CommonsConstants.HANDLE_USER+randomName+uid);
//        if(handleUser!=null&&!"".equals(handleUser)){
//            returnObject.setErrorCode(CommonsConstants.ONE);
//            returnObject.setErrorMessage("对不起!每件商品限购一件！");
//            return returnObject;
//        }
//        //根据固定的key中从Redis获取List数据的长度，如果这个长度大于限定数量则已经达到限流上限，限流上限可以固定或为库存的某个倍数
//        long limitingList= jedis.llen(CommonsConstants.LIMITING_LIST);
//        if(limitingList>=20000){
//            returnObject.setErrorCode(CommonsConstants.ONE);
//            returnObject.setErrorMessage("对不起!服务器繁忙请稍后再试！");
//            return returnObject;
//        }

//        如果没有超过限流值则将用户id作为值添加到Redis中的限流List中
        jedis.lpush(CommonsConstants.LIMITING_LIST,"1");
        //秒杀开始
        //针对秒杀的商品添加watch观察 判断这个值是否被某个线程修改
        jedis.watch(CommonsConstants.SECKILL_STORE+randomName);

        //获取redis中当前秒杀的商品数量（根据商品的随机名）
        store=Integer.parseInt(jedis.get(CommonsConstants.SECKILL_STORE+randomName));
        //如果商品库存大于0进入秒杀业务否则返回给与提示
        if(store>0){
            //秒杀业务
            //开启Redis的事务
            Transaction transaction= jedis.multi();
            //使用事务根据商品随机名减少1个库存
            transaction.decr(CommonsConstants.SECKILL_STORE+randomName);
            //执行事务
          List list=  transaction.exec();
//        如果事务执行返回值不为null 表示事务执行成功
            if(list!=null){//减少库存成功
                // 创建订单对象，并设定订单数据
                Order order=new Order();
                order.setUid(uid);
                order.setGoodsId(goodsId);
                order.setBuyNum(1);

//        将订单对象根据固定的key设置到Redis的List中（需要将订单对象转换程JSON数据）
                jedis.lpush(CommonsConstants.ORDER_LIST, JSONObject.toJSONString(order));
//        使用商品随机名+用户id作为可以将任意数据设置到Redis中（可以使用用户id作为值），目的是之前利用这个数据现在用户只能购买一次商品
                jedis.set(CommonsConstants.HANDLE_USER+randomName+uid,"1");
//        将订单的对象存入队列中（订单对象的JSON数据）用于，通知其他系统处理订单
                jmsTemplate.setDefaultDestinationName("secKill");
                jmsTemplate.send(session->session.createTextMessage(JSONObject.toJSONString(order)));
                returnObject.setErrorMessage("");
                returnObject.setErrorCode(CommonsConstants.ZERO);
                returnObject.setData("ok");
                return returnObject;
            }else {//库存减少失败，原因是其他线程修改过这个key
                jedis.unwatch();
                store=Integer.parseInt(CommonsConstants.SECKILL_STORE+randomName);
                if(store<=0){
                    returnObject.setErrorCode(CommonsConstants.ONE);
                    returnObject.setErrorMessage("对不起!商品已被抢光！");
                    return returnObject;
                }
                jedis.lpop(CommonsConstants.LIMITING_LIST);//减少当前用户的限流，否则接下来递归操作时一个用户可能占用2个限流位置
               return  this.secKill(uid,goodsId,randomName);
            }

        }else{
            jedis.lpop(CommonsConstants.LIMITING_LIST);//减少当前用户的限流，否则接下来递归操作时一个用户可能占用2个限流位置
            returnObject.setErrorCode(CommonsConstants.ONE);
            returnObject.setErrorMessage("对不起!商品已被抢光！");
            return returnObject;
        }
    }
}
