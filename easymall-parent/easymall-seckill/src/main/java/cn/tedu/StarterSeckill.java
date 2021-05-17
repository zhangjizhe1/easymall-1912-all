package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.seckill.mapper")
public class StarterSeckill {
    public static final String EXCHANGE="seckillEx";
    public static final String queue="seckillQ";
    public static final String routKey="seckill";
    //redis前缀也常用 key "product_query_"+id
    public static void main(String[] args) {
        SpringApplication.run(StarterSeckill.class,args);
    }
    //声明一些交换机,队列组件使用 seckill所有队列声明,交换机声明
    //总是代码中填写String类型字符串,交换机名字,队列名字,容易错
    //声明队列Queue
    @Bean
    public Queue queue01(){
        //queueDeclare()
        return new Queue(queue,false,false,false,null);
    }
    //声明交换机
    @Bean
    public DirectExchange ex01(){
        //exchangeDeclare
        return new DirectExchange(EXCHANGE);
    }
    //绑定组件关系
    @Bean
    public Binding bind01(){
        //queueBind(exname,routingkey);
        //将自定义各种组件实现绑定
        return BindingBuilder.bind(queue01()).to(ex01())
                .with(routKey);
    }
}
