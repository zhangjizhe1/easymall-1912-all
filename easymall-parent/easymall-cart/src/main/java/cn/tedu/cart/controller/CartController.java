package cn.tedu.cart.controller;

import cn.tedu.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    //根据用户id查询当前用户购物车所有购物车商品
    @RequestMapping("cart/manage/query")
    public List<Cart> queryMyCarts(String userId){
        return cartService.queryMyCarts(userId);
    }
    //新增购物车数据
    @RequestMapping("cart/manage/save")
    public SysResult addCart(Cart cart){
        try{
            cartService.addCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"新增购物车失败",null);
        }
    }

    //更新购物车商品数量
    @RequestMapping("cart/manage/update")
    public SysResult updateCartNum(Cart cart){
        try{
            cartService.updateCartNum(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"更新数量失败",null);
        }
    }

    //删除购物车数据
    @RequestMapping("cart/manage/delete")
    public SysResult deleteCart(Cart cart){
        try{
            cartService.deleteCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除失败",null);
        }
    }

}
