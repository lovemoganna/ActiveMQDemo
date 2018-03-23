package com.aier.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author: ligang
 * date: 2018/3/22
 * time: 17:08
 * 生产者
 */
@Service
public class ProducerService {
    private Logger logger = LoggerFactory.getLogger(ProducerService.class);
    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 根据目的地发送消息
     * @param destination
     */
    public void sendMessage(Destination destination,final String msg){

        try {
            System.out.println(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+msg);
            jmsTemplate.send(destination, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(msg);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 发送到默认的目的地
     */
    public void sendMessage(final String msg){
        try {
            String destination = jmsTemplate.getDefaultDestinationName();
            System.out.println(Thread.currentThread().getName()+" 向队列"+destination+"发送消息---------------------->"+msg);
            jmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(msg);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
