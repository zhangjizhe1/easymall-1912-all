package cn.tedu.seckill.service;

import cn.tedu.seckill.mapper.SeckillMapper;
import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;
    public List<Seckill> list() {
        //select * from seckill
        return seckillMapper.selectSeckills();
    }

    public Seckill detail(String seckillId) {
        return seckillMapper.selectSeckillById(seckillId);
    }

    public List<Success> checkSuccess(Long seckillId) {
        return seckillMapper.selectSuccessesBySeckillId(seckillId);
    }
}
