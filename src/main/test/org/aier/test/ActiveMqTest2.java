package org.aier.test;

import org.junit.Test;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author: ligang
 * date: 2018/3/22
 * time: 17:00
 * Topic广播模型
 */
public class ActiveMqTest2 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ConnectionFactory connectionFactory=null;
    Connection connection=null;
    MessageProducer messageProducer =null ;
    Session session=null;
    MessageConsumer consumer=null;
    TextMessage textMessage=null;
    @Test
    public void testTopicProducer() throws JMSException {
        try {
            // 依靠JMS规范
            // 1.创建一个连接工厂对象,需要指定服务的ip以及端口
            connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.220:61616");

            // 2.使用工厂对象创建一个Connection对象
             connection = connectionFactory.createConnection();

            // 3.开启连接,调用Connection对象的start方法
            connection.start();

            // 4.创建一个Session对象
            /**
             * 第一个参数:是否开启事务.如果true开启事务,第二个参数无意义,一般不开启事务false
             * 第二个参数:应答模式,自动应答或者手动应答.一般设置为自动应答.
             */
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 5.使用Session对象创建一个Destination对象,两种形式Queue,topic,现在使用topic
            Topic topic = session.createTopic("te-Topic");

            // 6.使用Session对象创建一个Producer对象
             messageProducer = session.createProducer(topic);

            // 7.创建一个Message对象,可以使用TextMessage.
                /*
                 * TextMessage textMessage = new ActiveMQTextMessage();
                 * textMessage.setText("hello ActiveMQ!");
                 */
             textMessage = session.createTextMessage("Topic massage!");

            // 8.发送消息
            messageProducer.send(textMessage);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            // 9.关闭资源
            messageProducer.close();
            session.close();
            connection.close();
        }
    }

    @Test
    public void testTopicConsumer() throws Exception{
        try {
            //1.创建一个ConnectionFactory对象连接MQ服务器
             connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.220:61616");
            //2.创建一个连接对象
             connection = connectionFactory.createConnection();
            //3.开启连接
            connection.start();
            //4.使用Connection对象创建一个Session对象
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //5.创建一个Destination对象.queue对象
            Topic topic = session.createTopic("te-Topic");
            //6.使用Session对象创建一个消费者对象
             consumer = session.createConsumer(topic);
            //7.接收消息
            consumer.setMessageListener(new MessageListener() {
                //当消息到达的时候,会调用这个方法
                @Override
                public void onMessage(Message message) {
                    //8.打印结果
                     textMessage= (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("topic消费者3已经启动!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            //等待接受消息
            System.in.read();//它会在这等待,打印出hello ActivqMQ!
            //9.关闭资源
            consumer.close();
            session.close();
            connection.close();
        }
    }
}