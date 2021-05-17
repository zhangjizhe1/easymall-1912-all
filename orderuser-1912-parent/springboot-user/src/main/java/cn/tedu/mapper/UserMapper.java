package cn.tedu.mapper;

import cn.tedu.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    void addPoints(@Param("user_id") String user_id, @Param("points") int points);
    User queryUserById(@Param("userId") String userId);
}
