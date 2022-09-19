package com.sys.login.tokenSecurity;

import com.alibaba.fastjson.JSON;
import com.sys.common.model.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 15:12
 * @description：  用来解决认证过的用户访问无权限资源时的异常
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(R.error(10003, "该账号无权限访问")));
        response.getWriter().flush();
    }
}