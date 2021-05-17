package cn.tedu.order.service;

import cn.tedu.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public void addOrder(Order order) {
        //处理新增可以从业务层处理多条写入(多次获取连接)
            //缺点:执行效率不高
            //优点:不需要关心mycat里的跨分片
        /*//新增主表
        orderMapper.addOrder(order);
        //新增子表
        for(OrderItem oi:orderItems){
            orderMapper.addOrderItems(oi);
        }*/
        //可以让持久层一次写入多条(效率更高,获取连接次数少 获取一次)
        //补齐数据orderId
        order.setOrderId(UUID.randomUUID().toString());
        orderMapper.insertOrder(order);
    }

    public List<Order> queryMyOrders(String userId) {
        //通过业务层处理
        //查询order
        /*List<Order> oList=om.selectOrder(userId);
        //每个order是缺少orderItems属性值
        for(Order o:oList){
            List<OrderItem>
        }*/
        //使用数据库ER表格结构的关联操作不需要关心跨分片
        return orderMapper.selectOrdersByUserid(userId);
    }

    public void deleteOrder(String orderId) {
        orderMapper.deleteOrderById(orderId);
    }
}
