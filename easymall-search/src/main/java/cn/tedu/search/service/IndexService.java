package cn.tedu.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.Product;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class IndexService {
    private static final ObjectMapper mapper=new ObjectMapper();
    @Autowired
    private TransportClient client;
    //读取es的索引,easydb 根据查询条件封装request将响应结果封装成返回对象
    public List<Product> search(String text, Integer page, Integer rows) {
        //构造query条件
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName", text);
        //构造查询的request对象,添加query条件,添加分页查询条件
        SearchRequestBuilder request = client.prepareSearch("easydb");
        SearchResponse response = request.setQuery(query).setFrom((page - 1) * rows).setSize(rows).get();
        //从response中解析数据
        List<Product> pList=new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for(SearchHit hit:hits){
            try{
                //每循环一次拿到一个包含了一个商品json的hit对象
                String productJson=hit.getSourceAsString();
                Product product=mapper.readValue(productJson,Product.class);
                //将解析的product对象添加到pList中
                pList.add(product);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return pList;
    }
}
