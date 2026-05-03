package com.bjpowernode.activemq.springboo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * ClassName:Receive
 * Package:com.bjpowernode.activemq.springboo
 * Description:
 *
 * @date:2018/10/18 10:50
 * @author:bjpowernode.com
 */
@Component("receive")
public class Receive {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void receive(){
        Message message=jmsTemplate.receive();
        if(message instanceof TextMessage){
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
