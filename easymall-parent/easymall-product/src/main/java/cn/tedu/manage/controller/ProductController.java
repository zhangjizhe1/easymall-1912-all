package cn.tedu.manage.controller;

import cn.tedu.manage.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@Autowired
	private ProductService ps;
	//负责接收请求实现返回分页查询数据功能
	@RequestMapping("product/manage/pageManage")
	public EasyUIResult queryByPage(Integer page, Integer rows){
        /*请求传递过来的2个参数
        page 表示分页的页数
        rows 表示每一页展示的条数
         */
		EasyUIResult result=ps.queryByPage(page,rows);
		//result2个情况 null 没封装对,有数据total productList
		return result;
		//为什么EasyUIResult 因为ajax要求返回json javaScript object notation
		//对于ajax来讲可以直接接收变成js对象{"total":88,"rows":[{商品对象1的json},{},{}]}
	}
	//通过商品id值查询某个商品详细信息
	@RequestMapping("/product/manage/item/{productId}")
	public Product queryOneProduct(@PathVariable String productId){
		//调用业务层查询数据库返回商品对象
		return ps.queryOneProduct(productId);
	}

	//新增商品数据，写入的操作
	@RequestMapping("product/manage/save")
	//request参数中参数格式 productName=**&productPrice=300&...
	public SysResult addProduct(Product product){
		//写入的操作，关心业务层持久层能否正确成功的写入数据
		try{
			ps.addProduct(product);
			//成功执行返回status=200的对象
			return SysResult.ok();
		}catch(Exception e){
			//新增失败，或者程序异常
			return SysResult.build(201,"新增失败",null);
		}
	}
	//修改商品的数据
	@RequestMapping("product/manage/update")
	public SysResult editProduct(Product product){
		try{
			ps.editProduct(product);
			return SysResult.ok();
		}catch(Exception e){
			//修改失败
			return SysResult.build(201,"修改商品失败",null);
		}
	}
}
