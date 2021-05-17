package cn.tedu.controller;
import cn.tedu.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    private HiService hiService;
    //作为ribbon工程对外暴露的访问接口
    //实际上ribbon是调用service-hi来处理的逻辑
    //ribbon工程不会说英文 service-hi提供者会说hello
    @RequestMapping("ribbon/hello")
    public String sayHi(String name){
        return "RIBBON:"+hiService.sayHi(name);
    }
}
