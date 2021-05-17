package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    //注册功能的用户名重复校验
    @Autowired
    private UserService us;

    @RequestMapping("user/manage/checkUserName")
    public SysResult checkUserName(String userName){
        //调用业务层,业务层返回信息是true/false true表示可用false表示不可用
        boolean avalible=us.checkUserName(userName);
        if(avalible){
            //true表示可用
            return SysResult.ok();
        }else{
            //false不可用
            return SysResult.build(201,"用户名重复",null);
        }
    }

    //用户注册提交
    @RequestMapping("user/manage/save")
    public SysResult doRegister(User user){
        try{
            //调用业务层
            us.doRegister(user);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            //失败
            return SysResult.build(201,"注册用户失败",null);
        }
    }

    //登录校验
    @RequestMapping("user/manage/login")
    public SysResult doLogin(User user, HttpServletRequest request,
                             HttpServletResponse response){
        //控制层调用业务层,获取返回结果,redis的key值,叫ticket
        String ticket=us.doLogin(user);
        //不一定登录成功,用户名密码是错误的
        if("".equals(ticket)||ticket==null){
            //业务层没有正确生成redis的key值,登录失败
            return SysResult.build(201,"登录失败",null);
        }else{
            //说明登录ticket有值,业务层实现了在redis存储数据的过程
            //想办法将key返回给客户端浏览器存储 使用cookie
            //这里直接调用CookieUtils实现cookie的读写
            //cookie的名字必须EM_TICKET因为js会从cookie中读取EM_TICKET的值
            CookieUtils.setCookie(request,response,
                    "EM_TICKET",ticket);
            //返回登录成功信息
            return SysResult.ok();
        }
    }
    //通过ticket查询redis的数据
    @RequestMapping("user/manage/query/{ticket}")
    public SysResult queryUserJson(@PathVariable String ticket){
        //调用业务层获取userJson;
        String userJson=us.queryUserJson(ticket);
        //不一定获取到登录的用户json数据,有可能已经超时了
        if(userJson==null||"".equals(userJson)){
            //浏览器有cookie,但是redis没数据,登录超时 属于获取数据失败
            return SysResult.build(201,"登录已经超时,重新登录",null);
        }else{
            //登录状态正常使用
            return SysResult.build(200,"登录成功",userJson);
        }

    }
    //登出操作
    @RequestMapping("user/manage/logout")
    public SysResult doLogout(HttpServletResponse response,
                              HttpServletRequest request){
        //获取ticket
        String ticket=CookieUtils.getCookieValue(request,"EM_TICKET");
        if(ticket!=null&&!("".equals(ticket))){
            //说明有值,登录过,删除
            CookieUtils.deleteCookie(request,response,"EM_TICKET");
            //删除redis
            us.deleteTicket(ticket);
        }
        return SysResult.ok();
    }
}
