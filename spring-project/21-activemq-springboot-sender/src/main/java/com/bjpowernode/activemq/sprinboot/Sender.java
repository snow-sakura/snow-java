package com.bjpowernode.activemq.sprinboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * ClassName:Sender
 * Package:com.bjpowernode.activemq.sprinboot
 * Description:
 *
 * @date:2018/10/18 10:43
 * @author:bjpowernode.com
 */
@Service("sender")
public class Sender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(){
        jmsTemplate.setDefaultDestinationName("springBootMQ");
        jmsTemplate.send(session -> session.createTextMessage("SpringBoot 测试消息"));
    }
}
