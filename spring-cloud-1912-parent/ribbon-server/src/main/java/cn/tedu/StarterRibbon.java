package cn.tedu;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class StarterRibbon {
    public static void main(String[] args) {
        SpringApplication.run(StarterRibbon.class,args);
    }
    //创建一个容器的bean对象
    @Bean
    @LoadBalanced//添加ribbon的拦截逻辑，否则无法实现服务调用
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }
    @Bean
    public IRule initRule(){
        return new RandomRule();
    }
    /*@Bean
    public cn.tedu.bean.Bean initBean(){
        return new cn.tedu.bean.Bean();
    }*/
}
