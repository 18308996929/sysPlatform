package com.sys.library.config;

import com.alibaba.fastjson.JSON;
import com.sys.common.model.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 15:11
 * @description： 当未登录或者token失效访问接口时，自定义的返回结果。
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().printf(JSON.toJSONString(R.error(10002, "无效的token")));
        response.getWriter().flush();
    }
}