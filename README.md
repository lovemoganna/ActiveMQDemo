## 1.ActiveMQ是什么?

ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，

尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。

所以我们必须回到JMS身上,搞懂它是什么东西.前面我只是使用过ActiveMQ,光知道他的2种消息传递类型.现在系统的来一遍.


## 2.JMS的概念

JMS就是java消息队列.

JMS有2种通信模式:

1. 点对点的.即一个生产者和一个消费者一一对应.

2. 发布/订阅模式,即一个生产者产生消息并进行发送后,可以由多个消费者进行接收.

### 1.点对点的通信模型
**a.图示:**

![](https://upload-images.jianshu.io/upload_images/7505161-469d231e69a5bfa7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**b.涉及到的概念**

`在点对点通信模式中,应用程序由消息队列,发送方,接收方组成,每个消息队列都被发送到一个特定的队列,
接收者从队列中获取消息,队列保留这消息,直到他们被消费或者超时.`

**c.特点**

1. `每个消息只要一个消费者.`

2. `发送者和接收者在时间上是没有时间的约束,即发送者在发送完消息后,不管接收者有没有接收消息,都不会影响发送方发送消息到消息队列中.`

3. `发送方不管是否在发送消息，接收方都可以从消息队列中去到消息.`

4. `接收方在接收完消息之后，需要向消息队列应答成功.`

### 2.发布/订阅通信模型

**a.图示**

![](https://upload-images.jianshu.io/upload_images/7505161-b9ad7e1bce4f44a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**b.涉及到的概念**

`在发布/订阅消息模型中,发布者发布一个消息,该消息是topic传递给所有的客户端.`

`在该模式下,发布者和订阅者都是匿名的,即发布者与订阅者都不知道对方是谁.并且可以动态的发布和订阅Topic.`

`Topic主要用于保存和传递消息,且会一直保存消息直到消息被传递给客户端.`

**c.特点**

1. `一个消息可以传递多个订阅者.`

2. `发布者和订阅者具有时间约束,这一点和上面的点对点模式是相反的.
针对某个主题(Topic)的订阅者.它必须创建一个订阅者之后,才能消费发布者的消息.而且是为了消费消息,订阅者必须保持运行的状态.`

3. `为了缓和这样严格的时间相关性,JMS允许订阅者创建一个可持久化的订阅,这样,即使订阅者没有被激活(运行),他也能接收到发布者的消息.`

## JMS接收消息

JMS中,消息的产生和消费是异步的.

对于消费来说,JMS的消费可以通过两种方式来消费消息.

1.同步(Synchronous)

`在同步消费信息模式中,订阅者/接收方通过调用receive()方法来接收消息.`

`在receive()方法中.线程会阻塞直到消息到指定时间后,消息仍未到达.`

2.异步(Asynchronous)

`使用异步方式接收消息的话，消息订阅者需注册一个消息监听者，类似于事件监听器，只要消息到达，JMS服务提供者会通过调用监听器的onMessage()递送消息。`

## JMS的编程模型

图示:

![](https://upload-images.jianshu.io/upload_images/7505161-822fd7708784c1af.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

主要对象:

1.管理对象(Administered)--连接工厂(ConnectionFactory)--目的地(Destination)

ConnectionFactory

`创建Connection对象的工厂，针对两种不同的jms消息模型，分别有QueueConnectionFactory和TopicConnectionFactory两种。`

Destination

`目的地指明消息被发送的目的地以及客户端接收消息的来源。JMS使用两种目的地，队列和话题。`

2.连接对象(Connection)

`Connection表示在客户端和JMS系统之间建立的链接（对TCP/IP socket的包装）。`

`Connection可以产生一个或多个Session。`

`跟ConnectionFactory一样，Connection也有两种类型：QueueConnection和TopicConnection。`

3.会话(Sessions)

`Session 是我们对消息进行操作的接口，可以通过session创建生产者、消费者、消息等。`

`Session 提供了事务的功能，如果需要使用session发送/接收多个消息时，可以将这些发送/接收动作放到一个事务中。`

```androiddatabinding
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
/**
 *  第一个参数:是否开启事务.如果true开启事务,第二个参数无意义,一般不开启事务false
 *  第二个参数:应答模式,自动应答或者手动应答.一般设置为自动应答.
 */

```
4.消息生产者(Message Producers)

`消息生产者由Session创建，用于往目的地发送消息。`

`生产者实现MessageProducer接口，我们可以为目的地、队列或话题创建生产者；`


5.消息消费者(Message Consumers)

`消息消费者由Session创建，用于接收被发送到Destination的消息。`

6.消息监听者(Message Listeners)

`消息监听器。如果注册了消息监听器，一旦消息到达，将自动调用监听器的onMessage方法。`

`EJB中的MDB（Message-Driven Bean）就是一种MessageListener。`


### 进入ActiveMQ

虚拟机配置: 之前配置过.不做赘述.

浏览器访问地址: url: `http://192.168.25.220:8161/admin/`
## 测试

测试代码在`ActiveMQDemo\src\main\test`目录下

## 测试ActiveMQ的目的地为Queue类型的情况

消息发送者的测试:

 就相当于老板(生产者)-发送消息给秘书(MQ服务).


![](https://upload-images.jianshu.io/upload_images/7505161-0994e35255fd5360.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](https://upload-images.jianshu.io/upload_images/7505161-4f6edb201736575e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

消息接收者的测试:

就相当于员工(消费者)从秘书(MQ)接收消息.

![](https://upload-images.jianshu.io/upload_images/7505161-8613dc138f517d18.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

消费者方法一直开启,再生产一个消息,就会再次消费新产生的这个消息.

![](https://upload-images.jianshu.io/upload_images/7505161-70a599f68c8267a6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 测试ActiveMQ的目的地为Topic类型的情况

生产者测试:

还是那句话:生产者只管生产消息.

与Queue不同,目的地为Topic类型,消息如果不被消费,就会消失,并不会被保存在服务端.

![](https://upload-images.jianshu.io/upload_images/7505161-5020b1bb0149e4a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

多个消费者测试:

先启动3个消费者.(印证了上面的说明,此时并不会有消息消费.)

再生产一个消息,

测试结果展示3个消费者都能接收到消息.

消费者1测试结果,其他两个跟下面类似.
```
"C:\Program Files\Java\jdk-9.0.1\bin\java" -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:F:\IDEA\IntelliJ IDEA 2017.2.5\lib\idea_rt.jar=53492:F:\IDEA\IntelliJ IDEA 2017.2.5\bin" -Dfile.encoding=UTF-8 -classpath "F:\IDEA\IntelliJ IDEA 2017.2.5\lib\idea_rt.jar;F:\IDEA\IntelliJ IDEA 2017.2.5\plugins\junit\lib\junit-rt.jar;F:\IDEA\IntelliJ IDEA 2017.2.5\plugins\junit\lib\junit5-rt.jar;E:\Article\ActiveMQDemo\target\test-classes;E:\Article\ActiveMQDemo\target\classes;C:\Users\Administrator\.m2\repository\junit\junit\4.11\junit-4.11.jar;C:\Users\Administrator\.m2\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;C:\Users\Administrator\.m2\repository\org\slf4j\slf4j-api\1.7.12\slf4j-api-1.7.12.jar;C:\Users\Administrator\.m2\repository\ch\qos\logback\logback-core\1.1.1\logback-core-1.1.1.jar;C:\Users\Administrator\.m2\repository\ch\qos\logback\logback-classic\1.1.1\logback-classic-1.1.1.jar;C:\Users\Administrator\.m2\repository\jstl\jstl\1.2\jstl-1.2.jar;C:\Users\Administrator\.m2\repository\javax\servlet\servlet-api\2.5\servlet-api-2.5.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-core\4.1.8.RELEASE\spring-core-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-context\4.1.8.RELEASE\spring-context-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-aop\4.1.8.RELEASE\spring-aop-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-beans\4.1.8.RELEASE\spring-beans-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-expression\4.1.8.RELEASE\spring-expression-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-tx\4.1.8.RELEASE\spring-tx-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-webmvc\4.1.8.RELEASE\spring-webmvc-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-web\4.1.8.RELEASE\spring-web-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-jms\4.1.8.RELEASE\spring-jms-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\springframework\spring-messaging\4.1.8.RELEASE\spring-messaging-4.1.8.RELEASE.jar;C:\Users\Administrator\.m2\repository\org\apache\xbean\xbean-spring\3.16\xbean-spring-3.16.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\activemq-core\5.7.0\activemq-core-5.7.0.jar;C:\Users\Administrator\.m2\repository\org\apache\geronimo\specs\geronimo-jms_1.1_spec\1.1.1\geronimo-jms_1.1_spec-1.1.1.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\kahadb\5.7.0\kahadb-5.7.0.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\protobuf\activemq-protobuf\1.1\activemq-protobuf-1.1.jar;C:\Users\Administrator\.m2\repository\org\fusesource\mqtt-client\mqtt-client\1.3\mqtt-client-1.3.jar;C:\Users\Administrator\.m2\repository\org\fusesource\hawtdispatch\hawtdispatch-transport\1.11\hawtdispatch-transport-1.11.jar;C:\Users\Administrator\.m2\repository\org\fusesource\hawtdispatch\hawtdispatch\1.11\hawtdispatch-1.11.jar;C:\Users\Administrator\.m2\repository\org\fusesource\hawtbuf\hawtbuf\1.9\hawtbuf-1.9.jar;C:\Users\Administrator\.m2\repository\org\apache\geronimo\specs\geronimo-j2ee-management_1.1_spec\1.0.1\geronimo-j2ee-management_1.1_spec-1.0.1.jar;C:\Users\Administrator\.m2\repository\commons-net\commons-net\3.1\commons-net-3.1.jar;C:\Users\Administrator\.m2\repository\org\jasypt\jasypt\1.9.0\jasypt-1.9.0.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\activemq-pool\5.12.1\activemq-pool-5.12.1.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\activemq-jms-pool\5.12.1\activemq-jms-pool-5.12.1.jar;C:\Users\Administrator\.m2\repository\org\apache\activemq\activemq-client\5.12.1\activemq-client-5.12.1.jar;C:\Users\Administrator\.m2\repository\org\apache\geronimo\specs\geronimo-jta_1.0.1B_spec\1.0.1\geronimo-jta_1.0.1B_spec-1.0.1.jar;C:\Users\Administrator\.m2\repository\org\apache\commons\commons-pool2\2.4.2\commons-pool2-2.4.2.jar;C:\Users\Administrator\.m2\repository\commons-httpclient\commons-httpclient\3.1\commons-httpclient-3.1.jar;C:\Users\Administrator\.m2\repository\commons-codec\commons-codec\1.2\commons-codec-1.2.jar" com.intellij.rt.execution.junit.JUnitStarter -ideVersion5 -junit4 org.aier.test.ActiveMqTest2,testTopicConsumer
23:02:38.189 [ActiveMQ Transport: tcp:///192.168.25.220:61616@53498] DEBUG o.a.a.transport.WireFormatNegotiator - tcp:///192.168.25.220:61616@53498 after negotiation: OpenWireFormat{version=9, cacheEnabled=true, stackTraceEnabled=true, tightEncodingEnabled=true, sizePrefixDisabled=false, maxFrameSize=104857600}
23:02:38.599 [main] DEBUG o.a.a.thread.TaskRunnerFactory - Initialized TaskRunnerFactory[ActiveMQ Session Task] using ExecutorService: java.util.concurrent.ThreadPoolExecutor@2638011[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
topic消费者1已经启动!
23:02:58.186 [ActiveMQ InactivityMonitor WriteCheckTimer] DEBUG o.a.a.t.AbstractInactivityMonitor - WriteChecker 10005 ms elapsed since last write check.
Topic massage!
```
后台展示:

![](https://upload-images.jianshu.io/upload_images/7505161-a58c13c0caa2fea8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 总结

1.队列形式(点到点模式)

如果消息不接收,消息会默认的在服务端持久化.一直给你保存着.(永久保存),除非有消费者消费

2.Topic形式(广播模式)

消息是不持久化的,只有有活动的客户端,并且不管有多少个客户端,能接收到就接收到,接收不到,消息就会丢失.

广播模式也想持久化,在每个客户端启动的时候,设置一个Connection ID,就是一个编号,再在服务端注册.就可以在后台看到了消息是否被接收到了.








