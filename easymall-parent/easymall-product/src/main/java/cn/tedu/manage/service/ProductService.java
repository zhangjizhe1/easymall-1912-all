package cn.tedu.manage.service;

import cn.tedu.manage.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {
	@Autowired
	private ProductMapper pm;
	//生成一个返回的EasyUIResult对象 封装2个属性,读取数据库数据
	public EasyUIResult queryByPage(Integer page, Integer rows) {
		//首先准备一个对象
		EasyUIResult result=new EasyUIResult();//total=0 rows=null
		//先查询商品表格的总条数total,select count(*) from t_product;
		int total=pm.selectProductCount();
		//控制层,业务层方法名称与业务逻辑有关 持久层方法名称select insert delete update
		result.setTotal(total);//total=total rows=null
		//封装result的第二个属性rows List<Product> 通过page rows参数查询分页商品
		//select * from t_product limit start,rows rows=参数条数 start
		int start=(page-1)*rows;
		List<Product> pList=pm.selectProductsByPage(start,rows);
		//填写到result属性中
		result.setRows(pList);
		return result;
	}
	private ObjectMapper mapper=new ObjectMapper();
	@Autowired
	private StringRedisTemplate template;
	public Product queryOneProduct(String productId) {
		//在所有的缓存逻辑前添加锁的判断
		String lock=productId+".lock";
		if (template.hasKey(lock)) {
			//进入if说明锁存在,不能操作缓存逻辑读取数据库数据
			return pm.selectProductById(productId);
		}
		//生成对应数据的key值,唯一就行,得查询条件关联
		String productKey="product_query_"+productId;
		//判断存在
		if(template.hasKey(productKey)){
			//直接使用缓存数据,拿到json转化成对象返回
			try{
				//拿到缓存数据
				String pJson = template.opsForValue().get(productKey);
				//反序列化将json变成对象
				Product p = mapper.readValue(pJson, Product.class);
				return p;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}else {
			//不存在缓存数据,缓存未命中
			//先读取数据库
			Product p = pm.selectProductById(productId);
			//在缓存中添加一份,供其他访问使用
			//value=productJson
			try {
				//转化对象成为json
				String pJson = mapper.writeValueAsString(p);
				//一般商品超时2天
				template.opsForValue().set(productKey, pJson, 2, TimeUnit.DAYS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//调用持久层查询商品对象
			return p;
		}
	}

	public void addProduct(Product product) {
		//缺少productId 补上
		//id值全局唯一，这里自定义业务逻辑的字符串 谁在什么时间添加的什么类型的商品
		//UUID 每次生成uuid字符串都是不同的
		String productId= UUID.randomUUID().toString();
		System.out.println("当前商品生成uuid:"+productId);
		product.setProductId(productId);
		//新增商品对象数据
		pm.insertProduct(product);
		//redis也可以实现缓存
	}

	public void editProduct(Product product) {
		//准备一个锁,锁住缓存的当前商品数据使用
		String lock=product.getProductId()+".lock";
		template.opsForValue().set(lock,"");
		//缓存的数据删除
		String productKey="product_query_"+product.getProductId();
		template.delete(productKey);
		//将数据写入德奥数据库进行更新
		pm.updateProductById(product);
		//释放锁
		template.delete(lock);
	}
}
