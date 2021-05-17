package cn.tedu.controller;

import cn.tedu.service.OUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OUController {

    @Autowired
    private OUService ouService = null;
    @ResponseBody
    @RequestMapping("/pay.action")
    public String pay(String orderId){
        try {
            ouService.pay(orderId);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
