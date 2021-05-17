package cn.tedu.cart.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> selectCartsByUserid(String userId);

    Cart selectCartByUidAndPid(Cart cart);

    void updateCartNumByUidAndPid(Cart cart);

    void insertCart(Cart cart);

    void deleteCartByUidAndPid(Cart cart);
}
