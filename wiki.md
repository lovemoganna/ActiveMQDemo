## pom依赖

```
1.使用junit4,采用声明注解方式测试
2.日志选用slf4j和logback
3.JSP相关
4.spring相关
5.activemq
```

## 配置文件的说明

1.web.xml
```androiddatabinding
指定Spring 配置文件，springMvc 命名，编码格式.
```
2.applicationContext.xml
```androiddatabinding
applicationContext.xml 主要使用来装载Bean，指出包扫描路径：
```
3.spring-mvc.xml
```androiddatabinding
启用MVC注解,扫描Controller的路径,jsp视图解析器.
```
4.application-activeMQ.xml
```androiddatabinding
1.注意加上ActiveMq 中的DTD标签
2.amq:connectionFactory：用于配置我们链接工厂的地址和用户名密码，选择的是tcp连接.
3.jmsTemplate指定了连接工厂,默认消息发型目的地,连接时长,发布消息的方式.
```


## 测试发送消息
```androiddatabinding
http-nio-8080-exec-7 向队列null发送消息---------------------->hi MQ
DEBUG o.a.a.transport.WireFormatNegotiator - Sending: WireFormatInfo { version=11, properties={TcpNoDelayEnabled=true, SizePrefixDisabled=false, CacheSize=1024, StackTraceEnabled=true, CacheEnabled=true, TightEncodingEnabled=true, MaxFrameSize=9223372036854775807, Host=192.168.25.220, MaxInactivityDuration=30000, MaxInactivityDurationInitalDelay=10000}, magic=[A,c,t,i,v,e,M,Q]}
org.springframework.jms.connection.CachingConnectionFactory.initConnection Established shared JMS Connection: ActiveMQConnection {id=ID:XTM-01702051132-59834-1521760960331-1:1,clientId=null,started=false}
[ActiveMQ Transport: tcp:///192.168.25.220:61616@59835] DEBUG o.a.a.transport.InactivityMonitor - Using min of local: WireFormatInfo { version=11, properties={TcpNoDelayEnabled=true, SizePrefixDisabled=false, CacheSize=1024, StackTraceEnabled=true, CacheEnabled=true, TightEncodingEnabled=true, MaxFrameSize=9223372036854775807, Host=192.168.25.220, MaxInactivityDuration=30000, MaxInactivityDurationInitalDelay=10000}, magic=[A,c,t,i,v,e,M,Q]} and remote: WireFormatInfo { version=11, properties={CacheSize=1024, SizePrefixDisabled=false, TcpNoDelayEnabled=true, StackTraceEnabled=true, CacheEnabled=true, MaxFrameSize=104857600, TightEncodingEnabled=true, MaxInactivityDuration=30000, MaxInactivityDurationInitalDelay=10000}, magic=[A,c,t,i,v,e,M,Q]}
[ActiveMQ Transport: tcp:///192.168.25.220:61616@59835] DEBUG o.a.a.transport.WireFormatNegotiator - Received WireFormat: WireFormatInfo { version=11, properties={CacheSize=1024, SizePrefixDisabled=false, TcpNoDelayEnabled=true, StackTraceEnabled=true, CacheEnabled=true, MaxFrameSize=104857600, TightEncodingEnabled=true, MaxInactivityDuration=30000, MaxInactivityDurationInitalDelay=10000}, magic=[A,c,t,i,v,e,M,Q]}
[ActiveMQ Transport: tcp:///192.168.25.220:61616@59835] DEBUG o.a.a.transport.WireFormatNegotiator - tcp:///192.168.25.220:61616@59835 before negotiation: OpenWireFormat{version=11, cacheEnabled=false, stackTraceEnabled=false, tightEncodingEnabled=false, sizePrefixDisabled=false, maxFrameSize=9223372036854775807}
[ActiveMQ Transport: tcp:///192.168.25.220:61616@59835] DEBUG o.a.a.transport.WireFormatNegotiator - tcp:///192.168.25.220:61616@59835 after negotiation: OpenWireFormat{version=11, cacheEnabled=true, stackTraceEnabled=true, tightEncodingEnabled=true, sizePrefixDisabled=false, maxFrameSize=104857600}
[http-nio-8080-exec-7] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-7-----send to jms End
```
后台展示:

![](https://upload-images.jianshu.io/upload_images/7505161-a02ac9d3d3d1381f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 测试接收消息

```androiddatabinding
 [http-nio-8080-exec-4] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-4------------receive from jms Start
 [ActiveMQ Transport: tcp:///192.168.25.220:61616@59835] DEBUG o.a.a.thread.TaskRunnerFactory - Initialized TaskRunnerFactory[ActiveMQ Session Task] using ExecutorService: java.util.concurrent.ThreadPoolExecutor@2cedc23e[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
从队列queue://hiMQMQ收到了消息：	hi MQ
 [http-nio-8080-exec-4] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-4------------receive from jms End
```

后台展示:

![](https://upload-images.jianshu.io/upload_images/7505161-1575cfbbc2bf2ac9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 加入监听器

我们使用监听器来判断消息是否到达,这样可以很快的完成对消息的处理.

配置在applicationContext-ActiveMQ.xml里面.

测试结果:

发送POST请求发送2次消息:
```androiddatabinding
[http-nio-8080-exec-6] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-6-----send to jms Start
http-nio-8080-exec-6 向队列null发送消息---------------------->hi MQ
[http-nio-8080-exec-6] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-6-----send to jms End
QueueMessageListener监听到了消息：	hi MQ

http-nio-8080-exec-9 向队列null发送消息---------------------->hi MQ
[http-nio-8080-exec-9] INFO  c.aier.Controller.MessageController - http-nio-8080-exec-9-----send to jms End
QueueMessageListener监听到了消息：	hi MQ
```
消费者会自动消费消息.

![](https://upload-images.jianshu.io/upload_images/7505161-01c11139b69b4c8e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
