package com.bjpowernode.seckill.controller;

import com.bjpowernode.seckill.commons.CommonsConstants;
import com.bjpowernode.seckill.commons.CommonsReturnObject;
import com.bjpowernode.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:OrderController
 * Package:com.bjpowernode.seckill.controller
 * Description:
 *
 * @date:2018/10/26 11:33
 * @author:bjpowernode.com
 */
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/getOrderResult/{randomName}")
    public @ResponseBody CommonsReturnObject getOrderResult(@PathVariable String randomName){
        CommonsReturnObject returnObject=new CommonsReturnObject();
        Integer uid=1;
        returnObject= orderService.getOrderResult(uid,randomName);
        return returnObject;
    }
}
