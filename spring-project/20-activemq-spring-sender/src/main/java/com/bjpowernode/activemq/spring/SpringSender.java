package com.bjpowernode.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName:SpringSender
 * Package:com.bjpowernode.activemq.spring
 * Description:
 *
 * @date:2018/10/18 9:48
 * @author:bjpowernode.com
 */
@Service("springSender")
public class SpringSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sender(){
        jmsTemplate.send(session-> session.createTextMessage("Spring 的测试消息"));

    }
}
