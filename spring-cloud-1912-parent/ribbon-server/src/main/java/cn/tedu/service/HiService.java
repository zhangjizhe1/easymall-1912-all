package cn.tedu.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HiService {
    //调用service-hi的服务提供者8091 8092 8093 负载均衡调用
    @Autowired
    private RestTemplate template;
    public String sayHi(String name) {
        //发送请求
        //由于restTemplate对象在创建添加了ribbon的负载均衡逻辑
        //请求都会被拦截。判断域名为服务名称
        ResponseEntity<String> forEntity = template.getForEntity
                ("http://service-hi/client/hello?name=" + name,
                        String.class);

        return forEntity.getBody();
    }
}
