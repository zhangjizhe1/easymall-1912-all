package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableEurekaClient//eureka-client角色启动
public class StarterEurekaClient {
    public static void main(String[] args) {
        SpringApplication.run(StarterEurekaClient.class,args);
    }
}
