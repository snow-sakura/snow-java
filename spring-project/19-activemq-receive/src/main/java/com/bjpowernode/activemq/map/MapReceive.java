package com.bjpowernode.activemq.map;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ClassName:MapReceive
 * Package:com.bjpowernode.activemq.map
 * Description:
 *
 * @date:2018/10/16 9:48
 * @author:bjpowernode.com
 */
public class MapReceive {
    public static void main(String[] args) {
        receive();
    }

    private static void receive() {
        MessageConsumer consumer =null;
        Session session=null;
        Connection connection=null;
        try {
            //1.根据Broker地址创建连接工厂对象
            ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.31.128:61616");
            //2.创建连接对象
            connection=connectionFactory.createConnection();
            connection.start();//接收消息前前必须要start否则无法读取消息，线程会阻塞在receive()方法上
            //3.创建Session回话对象，
            // 参数1为是否使用事务性的消息 false表示不使用事务 true表示使用事务
            //参数2表示消息的确认机制，会影响消息的接收方法而不会影响发送方法Session.AUTO_ACKNOWLEDGE表示自动确认
            session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            //4.创建一个目的地对象，createQueue创建一个基于点对点的目的地对象，参数为目的地名称，需要与发送时对应
            Destination destination=session.createQueue("myMap");

            //5.创建消息的接收者 ,并设定从哪里获取消息
            consumer =session.createConsumer(destination);

            //6从消息服务中读取消息
            Message message= consumer.receive();
            //获取消息的具体内容
            if(message instanceof MapMessage){
                System.out.println("id="+((MapMessage) message).getLong("id"));
                System.out.println("name="+((MapMessage) message).getString("name"));
                System.out.println("age="+((MapMessage) message).getInt("age"));
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
