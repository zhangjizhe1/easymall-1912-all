package cn.tedu.cart.service;

import cn.tedu.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    public List<Cart> queryMyCarts(String userId) {
        return cartMapper.selectCartsByUserid(userId);
    }
    @Autowired
    private RestTemplate template;
    public void addCart(Cart cart) {
        /*
            1. 如何判断cart对象对于数据库来讲是新增还是更新
            2. 如果是新增，缺少3个字段从哪来
         */
        //cart对象userId productId(相当于主键唯一的值)
        //从数据库查询已有数据 select * from t-cart where userid= and productid=
        Cart exist=cartMapper.selectCartByUidAndPid(cart);
        //判断exist
        if(exist!=null){
            //说明已存在的用户商品，所以需要将传递的num和存在的num叠加，修改数据
            cart.setNum(cart.getNum()+exist.getNum());//cart参数中num就是最新num
            //调用update语句修改数据库内容update t-cart set num=#{num} where userid= and productid=
            cartMapper.updateCartNumByUidAndPid(cart);
        }else{
            //说明用户商品不存在，productName productImage productPrice补齐
            //productId可以利用这个id值访问调用productservice这个微服务
            //product/manage/item/{productId}
            String url="http://productservice/product/manage/item/"+cart.getProductId();
            Product product=template.getForObject(url, Product.class);
            //将属性补齐
            cart.setProductName(product.getProductName());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductImage(product.getProductImgurl());
            cartMapper.insertCart(cart);
        }
    }

    public void updateCartNum(Cart cart) {
        //直接调用已有的一个update方法
        cartMapper.updateCartNumByUidAndPid(cart);
    }

    public void deleteCart(Cart cart) {
        cartMapper.deleteCartByUidAndPid(cart);
    }
}
