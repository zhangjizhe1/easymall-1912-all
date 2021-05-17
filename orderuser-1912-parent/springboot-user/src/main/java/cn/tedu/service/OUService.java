package cn.tedu.service;

public interface OUService {
    /**
     * 查询用户积分
     * @param userId
     * @return
     */
    int getUserPoints(String userId);

    void updatePoint(Integer money, String userId);
}
