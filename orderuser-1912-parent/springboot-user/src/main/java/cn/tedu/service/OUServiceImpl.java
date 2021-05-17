package cn.tedu.service;

import cn.tedu.domain.User;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OUServiceImpl implements OUService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public int getUserPoints(String userId) {
        User user = userMapper.queryUserById(userId);
        return user.getPoints();
    }

    @Override
    public void updatePoint(Integer money, String userId) {
        //定义积分的逻辑
        int point=money;
        userMapper.addPoints(userId,point);
    }
}
