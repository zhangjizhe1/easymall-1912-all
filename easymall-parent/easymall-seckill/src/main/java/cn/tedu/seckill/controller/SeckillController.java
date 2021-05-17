package cn.tedu.seckill.controller;

import cn.tedu.StarterSeckill;
import cn.tedu.seckill.service.SeckillService;
import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeckillController {
    @Autowired
    private SeckillService seckillService;
    //查询秒杀所有商品数据
    @RequestMapping("seckill/manage/list")
    public List<Seckill> list(){
        return seckillService.list();
    }
    //查询单个秒杀商品,传递参数商品的seckillId
    @RequestMapping("seckill/manage/detail")
    public Seckill detail(String seckillId){
        return seckillService.detail(seckillId);
    }
    @Autowired
    private RabbitTemplate template;
    //开始秒杀
    @RequestMapping("seckill/manage/{seckillId}")
    public SysResult startSeckill(@PathVariable Integer seckillId){
        //本来用户登录逻辑下,发送秒杀,js传递userPhone
        //t_user 没有userPhone,没法实现.代码弥补一下每次随机生成一个电话号码
        String userPhone="1889977"+ RandomUtils.nextInt(9999);
        String msg=userPhone+"/"+seckillId;
        try{
            template.convertAndSend(StarterSeckill.EXCHANGE,StarterSeckill.routKey,msg);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.ok();
        }

    }

    @RequestMapping("seckill/manage/{seckillId}/userPhone")
    public List<Success> checkSuccess(@PathVariable Long seckillId){
        return seckillService.checkSuccess(seckillId);
    }
}
