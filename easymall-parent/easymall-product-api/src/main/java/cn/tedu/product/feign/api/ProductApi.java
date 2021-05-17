package cn.tedu.product.feign.api;

import com.jt.common.pojo.Product;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 只要依赖我这个工程,就能使用productApi对象
 * 操作访问product系统的单个商品查询
 */
@FeignClient(name="productservice")
public interface ProductApi {
    //访问商品单个商品查询
    @RequestMapping(value="/product/manage/item/{productId}",method = RequestMethod.GET)
    public Product queryProduct(@PathVariable(name = "productId") String productId);

}
