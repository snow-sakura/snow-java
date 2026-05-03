package com.bjpowernode.activemq.object;

import com.bjpowernode.activemq.model.User;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ObjectReceive
 * Package:com.bjpowernode.activemq.object
 * Description:
 *
 * @date:2018/10/16 9:31
 * @author:bjpowernode.com
 */
public class ObjectReceive {
    public static void main(String[] args) {
        receive();
    }

    private static void receive() {
        MessageConsumer consumer =null;
        Session session=null;
        Connection connection=null;
        try {
            //1.根据Broker地址创建连接工厂对象  （ActiveMQ提供的连接工厂）
            ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.31.128:61616");
            List<String> list=new ArrayList<String>();
            //定义受信任的包的列表（所有的类型都需要被ActiveMQ所信任才能接受）
            list.add("com.bjpowernode.activemq.model");
            list.add("java.lang");
            //注意：需要使用ActiveMQ的工厂对象调用方法，JMS的连接工厂中没有这个方法
            //设置受信任的包
            connectionFactory.setTrustedPackages(list);
            //2.创建连接对象
            connection=connectionFactory.createConnection();
            connection.start();//接收消息前前必须要start否则无法读取消息，线程会阻塞在receive()方法上
            //3.创建Session回话对象，
            // 参数1为是否使用事务性的消息 false表示不使用事务 true表示使用事务
            //参数2表示消息的确认机制，会影响消息的接收方法而不会影响发送方法Session.AUTO_ACKNOWLEDGE表示自动确认
            session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //4.创建一个目的地对象，createQueue创建一个基于点对点的目的地对象，参数为目的地名称，需要与发送时对应
            Destination destination=session.createQueue("myObject");

            //5.创建消息的接收者 ,并设定从哪里获取消息
            consumer =session.createConsumer(destination);

            //6从消息服务中读取消息
            Message message= consumer.receive();
            //获取消息的具体内容
            if(message instanceof ObjectMessage){
                User user= (User) ((ObjectMessage) message).getObject();
                System.out.println("id="+user.getId()+"   姓名="+ user.getName()+"   年龄="+user.getAge()  );
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(consumer!=null){
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if(session!=null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
