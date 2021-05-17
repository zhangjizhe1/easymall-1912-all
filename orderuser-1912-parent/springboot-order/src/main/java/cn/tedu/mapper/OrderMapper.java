package cn.tedu.mapper;

import cn.tedu.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    Order queryOrderById(@Param("order_id") String order_id);
}
