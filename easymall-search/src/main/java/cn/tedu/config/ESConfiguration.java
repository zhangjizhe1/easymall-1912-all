package cn.tedu.config;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration//注解所在的类相当于xml文件
public class ESConfiguration {
    //创建一个对象bean对象,交给容器
    @Bean
    public TransportClient init(){
        try{
            //初始化一个TransportClient对象
            TransportClient client=new PreBuiltTransportClient(Settings.EMPTY);
            //准备连接节点ip host TCP/IP 底层封装socket
            TransportAddress address1=new
                    InetSocketTransportAddress(
                    InetAddress.getByName("10.9.39.13"),9300);
            TransportAddress address2=new
                    InetSocketTransportAddress(
                    InetAddress.getByName("10.9.104.184"),9300);
            TransportAddress address3=new
                    InetSocketTransportAddress(
                    InetAddress.getByName("10.9.100.26"),9300);
            //将address 1 2 3 交给client使用
            client.addTransportAddresses(address1,address2,address3);
            return client;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
