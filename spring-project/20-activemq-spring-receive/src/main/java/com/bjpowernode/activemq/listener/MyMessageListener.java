package com.bjpowernode.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * ClassName:MyMessageListener
 * Package:com.bjpowernode.activemq.listener
 * Description:
 *
 * @date:2018/10/18 10:31
 * @author:bjpowernode.com
 */
public class MyMessageListener implements MessageListener {
    public void onMessage(Message message) {
        if(message instanceof TextMessage ){
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
