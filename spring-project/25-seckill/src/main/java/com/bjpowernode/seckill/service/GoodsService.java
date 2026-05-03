package com.bjpowernode.seckill.service;

import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.model.Goods;

import java.util.List;

/**
 * ClassName:GoodsService
 * Package:com.bjpowernode.seckill.service
 * Description:
 *
 * @date:2018/10/25 11:19
 * @author:bjpowernode.com
 */
public interface GoodsService {
    List<Goods> getAllGoods();

    Goods getGoodsByGoodsId(Integer goodsId);

    CommonsReturnObject secKill(Integer uid, Integer goodsId, String randomName);
}
