package cn.tedu.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    //注入StringRedisTemplate,操作redis
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("redis")
    public String setAndGet(String key,String value){
        //String类型数据操作
        ValueOperations<String, String> stringTemplate = stringRedisTemplate.opsForValue();
        //hash类型数据操作
        HashOperations<String, Object, Object> hashTemplate = stringRedisTemplate.opsForHash();
        //list类型
        ListOperations<String, String> listTemplate = stringRedisTemplate.opsForList();
        //set类型 zset类型
        stringTemplate.set(key,value);
        return stringTemplate.get(key);

        /*template.expire():expire
                template.hasKey():exists
                        template.getExpire():ttl*/
    }
}
