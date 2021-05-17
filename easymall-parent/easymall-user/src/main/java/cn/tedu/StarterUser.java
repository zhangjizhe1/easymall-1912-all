package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.user.mapper")
public class StarterUser {
    public static void main(String[] args) {
        SpringApplication.run(StarterUser.class,args);
    }


    /*@Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("10.42.113.114");
        jedisConnectionFactory.setPort(9000);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }*/
}
