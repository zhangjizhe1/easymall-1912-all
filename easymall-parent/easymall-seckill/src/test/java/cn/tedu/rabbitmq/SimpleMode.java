package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
简单模式中,可以实现一发,一接的代码测试案例使用多个Test运行代码
 表示生产端
 表示消费端
 */
public class SimpleMode {
    //为当前的测试准备一个连接对象 长连接connection,短链接channel
    private Channel channel;
    @Before
    public void initChan() throws Exception {
        //获取连接 必须提供 ip 端口 登录名密码.从连接工厂实现连接对象的获取
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("10.9.151.60");
        /*factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");*/
        //从连接工厂获取连接对象
        Connection conn = factory.newConnection();
        //从长连接中创建短连接对象给channel赋值
        channel=conn.createChannel();
    }
    //模拟生产端发送消息的逻辑
    //先不去自定义声明组件 交换机 队列都可以自定义声明,这里我们使用一个默认的
    //交换机 AMQP default 任何声明的队列 都会默认使用队列名称绑定这个交换机 direct
    @Test
    public void product() throws Exception {
        //准备一个即将发送的消息,需要是byte[]二进制
        String msg="明天后山交货,一手钱一手货,黑吃黑,不可能02";
        byte[] bytes = msg.getBytes();
        //声明一个即将使用的队列
        channel.queueDeclare("北京",false,false,false,null);
        /*queueDeclare 声明队列,如果同名队列已经存在,声明无效,不存在则rabbtimq创建队列
        queue:队列的名称,对于默认交换机AMQP default队列名称就是队列路由key
        durable:队列是否持久化,true持久化 rabbitmq重启队列依然存在,false不持久化,重启rabbitmq队列消失
        exclusive:队列是否专属,true表示专属,只有声明队列的connection操作该队列,别的connection
        无法使用这个队列,false表示不专属,connection都可以操作
        autoDelete:队列是否自动删除,true表示自动删除,在最后一个channel连接队列断开后,删除队列
        false表示不自动删除
        args:Map<String,Object> 表示队列各种附加属性
            例如:队列的最大消息长度,队列最大容量等等

         */
        //生产端 channel直接发送消息到交换机
        channel.basicPublish("","北京",null,bytes);
        /* basicPublish参数详解
        exchange:交换机的名称,生产端永远不能将消息直接发给队列 ""表示使用(AMQP DEFAULT)
        routingKey:消息携带的路由key(消息目的地).如果使用默认交换机,可以在路由key中
        填写队列的名字,每个对列在生成时都会默认使用队列名称绑定这个默认交换机
        props:BasicProperties类型,表示消息的属性.rabbitmq提供了一些消息可以携带属性信息
            比如:user_id,优先级,解析消息的字符集编码,等等
         */
    }

    //模拟消费端
    @Test
    public void consume() throws Exception {
        //监听队列,一旦发现消息,获取消息,处理消费逻辑system.out.println
        //新建一个消费对象
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //指定监听队列
        channel.basicConsume("北京",true,consumer);
        /*basicConsume方法详解
        queue:绑定的队列名称
        callback:绑定的消费端对象
        autoAck:是否自动确认
            false:需要手动返回消息确认数据
            true:消费端拿到一条消息,就立刻确认
         */
        //从消息队列中 获取消息,如果没有消息,线程挂起等待消息出现实现消费逻辑
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        //delivery就是java客户端封装的带有消息和消息属性的对象
        AMQP.BasicProperties properties = delivery.getProperties();
        System.out.println("优先级:"+properties.getPriority());
        System.out.println("content_type:"+properties.getContentType());
        System.out.println("content_encoding:"+properties.getContentEncoding());
        //打印发送消息体body
        byte[] body = delivery.getBody();
        System.out.println("消费端接收到消息:"+new String(body));
    }
}
