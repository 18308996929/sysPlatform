package com.sys.login.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.common.model.R;
import com.sys.common.model.userSecurity.UserAuthDetails;
import com.sys.login.properties.LoginResponseType;
import com.sys.login.properties.SecurityProperties;
import com.sys.login.tokenSecurity.JwtTokenUtil;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/18 16:50
 * @Description:    登录成功回调处理
 */
@Component("myAuthenctiationSuccessHandler")
public class MyAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("登录成功");
        UserAuthDetails userDetails = (UserAuthDetails) authentication.getPrincipal();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        // 生成token返回
        String token = jwtTokenUtil.generateToken(userDetails);
        // 基本信息 token 存 redis
        redisTemplate.opsForValue().set(token, userDetails,60L,TimeUnit.MINUTES);

        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(R.ok("登录成功").setData(token)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }



//        super.setDefaultTargetUrl("/getProductPage.html"); // 自己需要跳转的url 此为添加的代码
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}
