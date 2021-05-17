package cn.tedu.seckill.consumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
public class Consumer01 {
    //编写一个消费端逻辑方法,方法参数就是消息msg
    //使用一个注解,实现扫描注解,绑定底层监听逻辑
    @RabbitListener(queues={"queue01direct"})
    public void consume(String msg){
        //msg就是我们传递的String消息数据
        //执行消费逻辑
        System.out.println("消费端consumer01接收消息:"+msg);
    }
}
