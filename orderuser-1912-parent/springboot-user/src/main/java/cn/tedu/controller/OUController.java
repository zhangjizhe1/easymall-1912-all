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
    //USER系统,具备查询积分的功能没有变化
    @ResponseBody
    @RequestMapping("/point.action")
    public String point(String userId){
        try {
            //查询指定用户积分
            int point = ouService.getUserPoints(userId);
            //发送给用户
            return "{\"points\":\""+point+"\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"points\":\"-1\"}";
        }
    }
    //user系统中添加被order调用准备的一个更新积分的功能
    @RequestMapping("update.action")
    @ResponseBody
    public String updatePoint(Integer money,String userId){
        //调用业务层代码
        ouService.updatePoint(money,userId);
        return "1";
    }
    //pay订单支付功能不是user系统开发的,订单order系统中开发功能
    /*@ResponseBody
    @RequestMapping("/pay.action")
    public String pay(String orderId){
        try {
            ouService.pay(orderId);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }*/
}
