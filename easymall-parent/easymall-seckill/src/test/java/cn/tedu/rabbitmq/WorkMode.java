package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

/**
争抢模式,仅仅在简单模式代码案例中添加多个消费端同时监听一个队列的逻辑

 */
public class WorkMode {
    private Channel channel;
    @Before
    public void initChan() throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("10.9.151.60");
        Connection conn = factory.newConnection();
        channel=conn.createChannel();
    }
   @Test
    public void product() throws Exception {
        String[] hongbaos={"10.58元","5.12元","3.34元","0.01元"};
        channel.queueDeclare("朴老师的红包",false,false,false,null);
        System.out.println("朴老师发了四个红包,好大方啊");
        for(String hongbao:hongbaos){
            byte[] bytes=hongbao.getBytes();
            channel.basicPublish("","朴老师的红包",null,bytes);
        }
    }

    @Test
    public void consume01() throws Exception {
        QueueingConsumer consumer=new QueueingConsumer(channel);

        //指定监听队列
        channel.basicConsume("朴老师的红包",false,consumer);
        channel.basicQos(1);
        //prefetchCount 每次抓取消息个数,在没有返回确认之前最多可以抓取的消息
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        byte[] body = delivery.getBody();
        System.out.println("朴乾抢到:"+new String(body));
        //手动回执确认
        /*
            delivery 使用delivery的标签作为回执的信息
            multiple:是否一次性回执多条 false
         */
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
    }
    @Test
    public void consume02() throws Exception {
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //指定监听队列
        channel.basicConsume("朴老师的红包",false,consumer);
        channel.basicQos(1);
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        byte[] body = delivery.getBody();
        System.out.println("曹洋抢到:"+new String(body));
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
    }
    @Test
    public void consume04() throws Exception {
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //指定监听队列
        channel.basicConsume("朴老师的红包",false,consumer);
        channel.basicQos(1);
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        byte[] body = delivery.getBody();
        System.out.println("陈哲抢到:"+new String(body));
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
    }
    @Test
    public void consume03() throws Exception {
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //指定监听队列
        channel.basicConsume("朴老师的红包",false,consumer);
        channel.basicQos(1);
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        byte[] body = delivery.getBody();
        System.out.println("兰刚抢到:"+new String(body));
    }
}
