package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//不同的微服务包名 cn.tedu.user.* cn.tedu.pic.* cn.tedu.cart.*
@MapperScan("cn.tedu.manage.mapper")
public class StarterProduct {
    public static void main(String[] args) {
        SpringApplication.run(StarterProduct.class,args);
    }
}
