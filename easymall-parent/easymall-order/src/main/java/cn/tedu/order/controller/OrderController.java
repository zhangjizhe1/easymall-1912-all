package cn.tedu.order.controller;

import cn.tedu.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService os;
    //新增订单数据
    @RequestMapping("order/manage/save")
    public SysResult addOrder(Order order){
        try{
            os.addOrder(order);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"新增定案失败",null);
        }
    }
    //查询我的订单
    @RequestMapping("order/manage/query/{userId}")
    public List<Order> queryMyCarts(@PathVariable String userId){
        return os.queryMyOrders(userId);
    }

    //订单删除
    @RequestMapping("order/manage/delete/{orderId}")
    public SysResult deleteOrder(@PathVariable String orderId){
        try{
            os.deleteOrder(orderId);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除失败",null);
        }
    }
}
