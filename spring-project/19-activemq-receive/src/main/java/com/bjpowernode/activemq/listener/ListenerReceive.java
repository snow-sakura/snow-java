package com.bjpowernode.activemq.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ListenerReceive
 * Package:com.bjpowernode.activemq.listener
 * Description:
 *
 * @date:2018/10/16 11:50
 * @author:bjpowernode.com
 */
public class ListenerReceive {
    public static void main(String[] args) {
        receive();
        List<Integer> list=new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        System.out.println("========================");
        for(int i:list){
            System.out.println(i);
        }
        System.out.println("========================");
        list.forEach(i-> System.out.println(i));

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
            Destination destination=session.createQueue("myQueue");

            //5.创建消息的接收者 ,并设定从哪里获取消息
            consumer =session.createConsumer(destination);

//            //6设置监听器，异步读取消息
//            consumer.setMessageListener(new MessageListener() {
//                public void onMessage(Message message) {
//                    if(message instanceof  TextMessage){
//                        try {
//                            System.out.println(((TextMessage) message).getText());
//                        } catch (JMSException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
            /*
                Lambda 表达式实现异步接收消息， 注意：Lambda必须在Java8或以上版本才可使用
                Lambda 的目的是为了简化匿名内部类的实现，概念有点想JS中匿名方法参数,主要用于实现回调方法
                Lambda 使用前提
                        1.当我们调用的某个方法中的参数列表的类型有接口类型
                        2.这个接口中必须只能有一个抽象方法是时
                Lambda 语法1:
                       接口中的方法只有一个参数，我们对方法的实现只有一行代码，如果这个抽象方法拥有返回类型不需要添加return
                       形参 ->  方法体
                       语法2:
                       接口中的方法只有一个参数，形参列表必须添加(),
                       我们对方法的实现有多行代码，方法体必须添加{}
                       如果这个抽象方法拥有返回类型必须需要添加return
                       （形参1,形参2） ->  { 方法体}


             */

            consumer.setMessageListener(messages->{
                if(messages instanceof  TextMessage){
                    try {
                        System.out.println(((TextMessage) messages).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });


            System.out.println("监听器启动完成");
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
}
