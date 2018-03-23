package com.aier.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author: ligang
 * date: 2018/3/22
 * time: 17:10
 * 消费者
 */
@Service
public class ConsumerService {
    private Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 从目的地里面获取一条消息
     * @param destination
     * @return
     */
    public TextMessage receive(Destination destination){
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try{
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
        return textMessage;
    }
}
