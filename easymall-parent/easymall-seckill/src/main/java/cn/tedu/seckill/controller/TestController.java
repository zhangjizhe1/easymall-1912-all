package cn.tedu.seckill.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产端消息发送
 */
@RestController
public class TestController {
    //通过一次请求,controller接收到请求参数
    //将参数作为消息发送到已存在的交换机,携带可以用路由key
    //路由模式
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RequestMapping("send")
    public String send(String msg){
        //主要使用template对象来实现消息的发送
        //第一种方式直接发送消息 msg
        /*
            exchange:交换机名称 directEx
            routingKey:消息携带路由key
            object:消息数据 String msg
         */
        rabbitTemplate.convertAndSend("directEX","北京",msg);
        //第二种方式关心消息本身,还有消息属性 content_type priority..
        /*MessageProperties prop=new MessageProperties();
        prop.setAppId("1");
        prop.setPriority(100);
        prop.setContentType("text/html,charset=utd-8");
        Message message=new Message(msg.getBytes(),prop);
        rabbitTemplate.send("directEx","北京",message);
        */
        return "success";
    }
}
