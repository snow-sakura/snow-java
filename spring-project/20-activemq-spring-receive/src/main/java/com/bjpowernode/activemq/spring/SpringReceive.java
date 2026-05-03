package com.bjpowernode.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * ClassName:SpringReceive
 * Package:com.bjpowernode.activemq.spring
 * Description:
 *
 * @date:2018/10/18 10:23
 * @author:bjpowernode.com
 */
@Service("springReceive")
public class SpringReceive {
    @Autowired
    private JmsTemplate jmsTemplate;
    public void receive(){
        //设置消息的目的地名称，可用于发送和接收。如果在代码中指定的目的地，那么配置文件中的目的地将失效
        jmsTemplate.setDefaultDestinationName("springQueue");
//        设置目的地名称并接收数据
//        jmsTemplate.receive("springQueue");
        Message message= jmsTemplate.receive();
        if(message instanceof TextMessage){
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
