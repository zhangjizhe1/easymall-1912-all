package cn.tedu.service;

import cn.tedu.domain.Order;
import cn.tedu.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class OUServiceImpl implements OUService {

    @Autowired
    private OrderMapper orderMapper = null;
    @Autowired
    private RestTemplate template;
    @Override
    public void pay(String orderId) {
        //1.支付订单
        System.out.println("支付订单.."+orderId);
        Order order = orderMapper.queryOrderById(orderId);
        //TODO 在订单支付逻辑中考虑如何取调用user系统 /update.action请求
        //实现传递参数money userId实现让user系统管理积分更新功能
        //相当于order调用user的功能
        //准备一个访问ouu-server服务的请求地址 添加2个参数money userId
        //RestTemplate hah=new RestTemplate();
        int money=order.getOrder_money();
        String userId=order.getUser_id();
        String url="http://ouu-server/update.action?money="+money+
                "&userId="+userId;
        template.getForObject(url,String.class);
    }
}
