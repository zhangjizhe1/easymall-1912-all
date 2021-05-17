package cn.tedu.seckill.mapper;

import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;

import java.util.List;

public interface SeckillMapper {
    List<Seckill> selectSeckills();

    Seckill selectSeckillById(String seckillId);

    int updateNumber(Long seckillId);

    void insertSuc(Success suc);

    List<Success> selectSuccessesBySeckillId(Long seckillId);
}
