package cn.tedu.user.service;

import cn.tedu.user.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper um;
    public boolean checkUserName(String userName) {
        //先从数据库查询存在不存在
        int exist=um.selectCountByUserName(userName);
        return exist!=1;
    }

    public void doRegister(User user) {
        //补充userId值
        user.setUserId(UUID.randomUUID().toString());
        //加密密码
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));//明文进行加密计算
        //加盐+salt 例如:明文密码123456,不使用原明文做加密 12dkfah34dkfsdk56sdkfkjd
        um.insertUser(user);
    }
    private ObjectMapper mapper =new ObjectMapper();
    @Autowired
    private StringRedisTemplate template;
    public String doLogin(User user) {
        //验证合法性 select * from t_user where user_name=#{} and pssword=#{}
        //对user的密码实现加密处理
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        //持久层查询用户数据
        User exist=um.selectUserByUserNameAndPassword(user);
        //准备一个返回的String 的ticket值
        String ticket="";
        if(exist==null){
            //用户名密码是无效的没查到数据
            return ticket;
        }else{//exist不为空,用户秘密码合法,在redis存储数据
            //计算一个ticket值 作为一个用户的key值,具备的特点
                //用户不同key值不能一样,同一个用户不同时间登录,key值也不一样 登录顶替
            ticket="EM_TICKET_"+user.getUserName()+System.currentTimeMillis();
            //value值 userJson 使用jackson转化,将user对象转化为json字符串
            //添加一个以用户名为值的登录key
            String loginKey="login_"+user.getUserName();
            //经过是否顶替的逻辑才能进行数据的写入
            if(template.hasKey(loginKey)){
                //有这个值,说明有人登录过,拿到上一次登录的ticket
                String ticketLaster=template.opsForValue().get(loginKey);
                //删除上一次的ticket和userJson
                template.delete(ticketLaster);
            }
            try{
                //创建一个jackson给提供的对象
                //ObjectMapper mapper=new ObjectMapper();
                //存储在redis的数据value应该是当前登录的用户信息,将exist转化的String
                String userJson=mapper.writeValueAsString(exist);
                //{"userNamep":"wang","userEmail":"1@11.com","userNickname":"admin"}
                //使用template存储数据 key value 超时存储 2小时超时时间
                template.opsForValue().set(ticket,userJson,
                        60*60*2, TimeUnit.SECONDS);
                //将loginKey保存好本次登录生成的ticket 让别人登录时也可以顶替本次数据
                template.opsForValue().set(loginKey,ticket,
                        60*60*2,TimeUnit.SECONDS);
                return ticket;
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    public String queryUserJson(String ticket) {
        //利用template读一下数据就可以了
        //添加续租的逻辑
        //获取ticket的剩余时间,如果发现剩余时间小于1小时,重新设置超时时长2小时
        Long leftTime = template.getExpire(ticket, TimeUnit.SECONDS);
        //判断剩余是否小于1小时
        if(leftTime<60*60){
            //进入if说明剩余不足1小时,重新设置超时
            template.expire(ticket,60*60*2,TimeUnit.SECONDS);
        }
        return template.opsForValue().get(ticket);

    }
    public void deleteTicket(String ticket) {
        template.delete(ticket);
    }
}
