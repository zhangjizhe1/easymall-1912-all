package cn.tedu;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    //测试restTemplate

    @Test

    public void test02(){

        RestTemplate restTemplate=new RestTemplate();

        //访问京东 京东首页返回的响应

        //url: string类型，请求url地址

        //responseType:class类型，响应体内容解析对象 String.class

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.jd.com/", String.class);

        System.out.println(responseEntity.getBody());//拿到响应体

        responseEntity.getStatusCode();

        HttpHeaders headers = responseEntity.getHeaders();

    }



    //restTemplate访问8091的功能client/hello

    @Test

    public void test03(){

        RestTemplate restTemplate=new RestTemplate();

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8093/client/hello?name=王老师", String.class);

        System.out.println(forEntity.getBody());

    }

}
