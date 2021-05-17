package cn.tedu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/*@Component*/
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //过滤之前的判断逻辑,所有请求都要过滤
        return true;
    }

    @Override
    public Object run() {
        System.out.println("run方法被调用,过滤器实现");
        //根据请求参数是否携带token决定是否放行
        //获取当前请求的上下文对象context,包含请求对象,响应对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //HttpServletResponse response = currentContext.getResponse();
        //获取request中的参数token如果为空,说明需要拒绝访问
        String token = request.getParameter("token");
        if(token==null||"".equals(token)){
            //说明当前请求没有权限,response拒绝访问返回错误信息
            currentContext.setSendZuulResponse(false);//是否继续向后发送请求访问微服务
            //请求返回的内容,错误信息 {"":""}
            currentContext.setResponseStatusCode(401);//401 403无权限
            currentContext.setResponseBody("{\"status\":\"201\",\"msg\":\"you are not authorized\"}");
        }
        return null;//返回值没有用处
    }
}
