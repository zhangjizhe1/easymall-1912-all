package cn.tedu.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {

    int selectCountByUserName(String userName);

    void insertUser(User user);

    User selectUserByUserNameAndPassword(User user);
}
