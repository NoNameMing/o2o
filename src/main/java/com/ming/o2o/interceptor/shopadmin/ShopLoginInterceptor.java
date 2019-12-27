package com.ming.o2o.interceptor.shopadmin;

import com.ming.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 做事前拦截，即用户操作发生前，改写 preHandle 里的逻辑，进行拦截
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception{
        // 从 session 中取出用户来
        Object userObj = request.getSession().getAttribute("user");
        if(userObj != null) {
            // 若用户信息不为空则将 session 里的用户信息转化为 PersonInfo 实体类
            PersonInfo user = (PersonInfo) userObj;
            // 做空值判断，确保 ownerId 不为空且该账号的可用状态为1，并且用户类型为店家
            if (user != null && user.getOwnerId() != null && user.getOwnerId() > 0
                && user.getEnableStatus() == 1) {
                // 若通过验证则返回 true，拦截器返回 true 后，用户接下来的操作得意执行
                return true;
            }
        }
        // 若不满足登录验证，则直接跳转到帐号登录界面
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html >");
        return false;
    }
}
