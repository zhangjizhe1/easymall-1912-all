package cn.tedu.manage.mapper;

import com.jt.common.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {
	//@Select("select count(*) from t_product")
	int selectProductCount();
	//在映射文件中要使用#{start} #{rows}
	//@Select("select * from t_product limit #{start},#{rows}")
	List<Product> selectProductsByPage(@Param("start")int start, @Param("rows")Integer rows);

	Product selectProductById(String productId);

	void insertProduct(Product product);

	void updateProductById(Product product);

}
