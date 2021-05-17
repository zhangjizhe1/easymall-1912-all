package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HiController {
    //properties 读一个属性8091
    @Value("${server.port}")
    private Integer port;
    //请求地址
    @RequestMapping(value="/client/hello",method= RequestMethod.GET)
    public String sayHi(@RequestParam("name")String name){
        //添加一个端口，为负载均衡调用做准备
        return "hello "+name+",I am eureka client "+port;
    }
}
