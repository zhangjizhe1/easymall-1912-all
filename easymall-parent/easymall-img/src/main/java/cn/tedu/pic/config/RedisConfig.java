package cn.tedu.pic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {
    //创建出来一个操作redis的集群对象
    @Bean
    public JedisCluster initJedisCluster(){
        //连接8000 8001 8002 8003 8004 8005对象返回就可以在系统中
        //使用了
        Set<HostAndPort> infos=new HashSet<>();
        infos.add(new HostAndPort("10.42.113.114",8000));
        //构造jedisCluster
        JedisCluster cluster=new JedisCluster(infos);
        return cluster;
    }
}
