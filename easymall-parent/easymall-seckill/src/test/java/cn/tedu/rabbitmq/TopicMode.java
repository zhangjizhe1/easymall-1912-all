package cn.tedu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

/**
声明一个主题模式的交换机
 使用多个队列绑定这个交换机
 队列的路由key是范围匹配值 * #代替字符串实现多级,单级匹配
 */
public class TopicMode {
    private Channel channel;
    @Before
    public void initChan() throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("10.9.151.60");
        Connection conn = factory.newConnection();
        channel=conn.createChannel();
    }
    private static final String type="topic";
    private static final String EX=type+"EX";
    private static final String Q01="queue01"+type;
    private static final String Q02="queue02"+type;
    @Test
    public void product() throws Exception {
        String msg="主题模式你好";
        byte[] bytes = msg.getBytes();
        //声明多个队列
        channel.queueDeclare(Q01,false,false,false,null);
        channel.queueDeclare(Q02,false,false,false,null);
        //声明交换机/或者你也可以直接绑定队列到已有的fanout类型交换机amq.fanout
        channel.exchangeDeclare(EX,type);//声明一个叫做fanoutEX的fanout类型交换机
        //实现绑定,队列绑定交换机时要使用routingkey,fanout无论你队列绑定什么routingkey
        //发布订阅只要交换机得到消息一定会发送给这些后端绑定队列
        channel.queueBind(Q01,EX,"北京.*");
        channel.queueBind(Q02,EX,"上海.#");
        //生产端只负责发送消息到交换机,至于交换机怎么和队列交互,交换机类型不一样
        //交互逻辑也不同
        channel.basicPublish(EX,"北京.朝阳",null,bytes);
        channel.basicPublish(EX,"北京.朝阳.海淀",null,bytes);
        channel.basicPublish(EX,"上海.浦东.华南路",null,bytes);
    }
}
