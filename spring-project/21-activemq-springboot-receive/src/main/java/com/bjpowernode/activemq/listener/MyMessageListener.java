package com.bjpowernode.activemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyMessageListener
 * Package:com.bjpowernode.activemq.listener
 * Description:
 *
 * @date:2018/10/18 10:53
 * @author:bjpowernode.com
 */
@Component
public class MyMessageListener {

    @JmsListener(destination = "springBootMQ")
    public void onMessage(String context){
        System.out.println(context);
    }
}
