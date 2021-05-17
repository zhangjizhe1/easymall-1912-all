package cn.tedu.seckill.consumer;

import cn.tedu.StarterSeckill;
import cn.tedu.seckill.mapper.SeckillMapper;
import com.jt.common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SeckillConsumer {
    //注入redis客户端
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private SeckillMapper seckillMapper;
    @RabbitListener(queues= StarterSeckill.queue)
    public void consume(String msg){
        /*消费逻辑
        1.判断用户秒杀的商品是否可以减库存
            1.1可以减库存 没卖完可以秒杀
            1.2不可以减库存,买完了,秒杀失败
        2.减库存成功,相当于秒杀信息记录succes userPhone seckillId
         */
        //从消息中解析2个值userPhone seckillId Long
        Long userPhone=Long.parseLong(msg.split("/")[0]);
        Long seckillId=Long.parseLong(msg.split("/")[1]);
        //执行更新之前通过redis的decr判断是否可以执行update语句
        //判断一下是否存在该商品的redis减库存的key值
        if(!template.hasKey("num_"+seckillId)){
            //key不存在,一个删除了,秒杀结束,一个没创建秒杀还没开始
            System.out.println("您想秒杀的商品暂时不能秒杀了");
        }
        //如果if没进来,减库存
        Long increment = template.opsForValue().increment("num_" + seckillId, -1);
        if(increment<0){
            //说明商品已经卖完了
            System.out.println("用户:"+userPhone+",秒杀当前商品:"+seckillId+"失败");
        }
        //减库存操作 条件包括哪些
        //允许库存number-1执行的条件where有哪些
            /*第一个因素:时间因素,当前时间必须在有效时间范围内 start<now<end
              第二个因素:number大于等于0 如果小于0 -5 多卖了5个.
             */
        //执行sql语句 update seckill set number=number-1 where seckill_id= and
        //number>0 and nowTime<end and nowTime>start now()
        int result=seckillMapper.updateNumber(seckillId);
        //判断result
        if(result==0){
            //减库存没成功 秒杀失败
            System.out.println("用户:"+userPhone+",秒杀当前商品:"+seckillId+"失败");
        }else{
            //说明秒杀成功,将数据封装到一个Success对象写入success表格
            //项目Success结构和succes对应不是很正确,手动修改一下
            Success suc=new Success();
            suc.setCreateTime(new Date());
            suc.setUserPhone(userPhone);
            suc.setSeckillId(seckillId);
            suc.setState(0);
            //insert写入
            seckillMapper.insertSuc(suc);
        }
    }
}
