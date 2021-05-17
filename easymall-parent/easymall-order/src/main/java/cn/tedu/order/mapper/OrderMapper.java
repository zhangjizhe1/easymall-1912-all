package cn.tedu.order.mapper;

import com.jt.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    void insertOrder(Order order);

    List<Order> selectOrdersByUserid(String userId);

    void deleteOrderById(String orderId);
}
