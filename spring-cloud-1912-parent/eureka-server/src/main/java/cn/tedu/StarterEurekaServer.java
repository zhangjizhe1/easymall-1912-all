package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableEurekaServer
public class StarterEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(StarterEurekaServer.class,args);
        Map<String, Map<String,Instance>> param=new HashMap<>();
        //记录一个实例注册在注册中心service-hi服务
        Map<String,Instance> instance1=new HashMap<>();
        //接收请求参数ip 端口 时间戳
        Instance ins8091=new Instance();
        ins8091.setHost("10.9.39.13");
        ins8091.setPort(8091);
        ins8091.setTimestamp(new Date());
        instance1.put("localhost:serivce-hi:8091",ins8091);
        param.put("service-hi",instance1);


    }
}
