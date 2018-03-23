package com.aier.Controller;

import com.aier.Service.ConsumerService;
import com.aier.Service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * @author: ligang
 * date: 2018/3/22
 * time: 17:11
 */
@Controller
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    /**
     * 注入目的地
     */
    @Autowired
    private Destination destination;

    /**
     * 队列消息生产者
     */
    @Autowired
    private ProducerService producer;

    /**
     * 队列消息消费者
     */

    @Autowired
    private ConsumerService consumer;

    /**
     * 发送消息
     * @param msg
     */
    @RequestMapping(name = "/SendMessage", method = RequestMethod.POST)
    @ResponseBody
    public void send(String msg) {
        logger.info(Thread.currentThread().getName()+"-----send to jms Start");
        producer.sendMessage("hi MQ");
        logger.info(Thread.currentThread().getName()+"-----send to jms End");
    }

    /**
     * 接收消息
     * @return
     */
    @RequestMapping(name= "/ReceiveMessage",method = RequestMethod.GET)
    @ResponseBody
    public Object receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(destination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        return tm;
    }
}
