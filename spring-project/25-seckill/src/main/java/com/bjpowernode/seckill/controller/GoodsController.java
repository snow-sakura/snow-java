package com.bjpowernode.seckill.controller;

import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ClassName:GoodsController
 * Package:com.bjpowernode.seckill.controller
 * Description:
 *
 * @date:2018/10/25 11:57
 * @author:bjpowernode.com
 */
@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/")
    public String  index(Model model){
       List<Goods>list= goodsService.getAllGoods();
        model.addAttribute("goodsList",list);
        return "index";
    }
    @GetMapping("/goodsDetail/{goodsId}")
    public String goodsDetail(@PathVariable Integer goodsId,Model model){
        Goods goods= goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        return "goodsDetail";
    }
    @RequestMapping("/getSystemTime")
    public @ResponseBody CommonsReturnObject getSystemTime(){
        //定义Ajax的json封装对象，并设置数据
        CommonsReturnObject returnObject=new CommonsReturnObject();
        returnObject.setErrorCode(CommonsConstants.ZERO);
        returnObject.setErrorMessage("");
        //获取当前系统时间的毫秒数
        returnObject.setData(System.currentTimeMillis());
        return returnObject;
    }

    @RequestMapping("/doSecKill/{goodsId}/{startTime}/{endTime}/{randomName}")
    public @ResponseBody CommonsReturnObject doSecKill(@PathVariable Integer goodsId,@PathVariable Long startTime,@PathVariable Long endTime,@PathVariable String randomName){
        //定义Ajax的json封装对象，并设置数据
        CommonsReturnObject returnObject=new CommonsReturnObject();
        long nowTime=System.currentTimeMillis();
        if(endTime<nowTime){
            returnObject.setErrorMessage("对不起！秒杀已经结束！");
            returnObject.setErrorCode(CommonsConstants.ONE);
            returnObject.setData(null);
            return returnObject;
        }
        //当前登录用户的Session中的id
        Integer uid=1;

        returnObject=goodsService.secKill(uid,goodsId,randomName);

        return returnObject;
    }
}
