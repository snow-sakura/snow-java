package com.bjpowernode.mycat.controller;

import com.bjpowernode.mycat.mapper.GoodsMapper;
import com.bjpowernode.mycat.mapper.StuMapper;
import com.bjpowernode.mycat.model.Goods;
import com.bjpowernode.mycat.model.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:StuController
 * Package:com.bjpowernode.mycat.controller
 * Description:
 *
 * @date:2018/10/22 10:31
 * @author:bjpowernode.com
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsMapper goodsMapper;
    @RequestMapping("/goods/insert")
    public String insert(){
        Goods goods=new Goods();
        goods.setName("手机");
//        for(int i=0;i<97;i++){
            goodsMapper.insert(goods);
//        }

        return "商品添加成功";
    }
}
