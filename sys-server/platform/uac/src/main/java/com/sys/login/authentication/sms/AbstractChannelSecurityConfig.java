package com.sys.login.authentication.sms;

import com.sys.login.properties.SecurityConstants;
import com.sys.login.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 15:55
 * @Description:    基础的登录配置
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler myAuthenticationFailureHandler;


    @Autowired
    private SecurityProperties securityProperties;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)                      // 后台接口对应接口配置
                // .loginPage(securityProperties.getBrowser().getLoginPage()) //web端配置一个页面:  自定义登录页面
                // .defaultSuccessUrl("/demo-login-sucess.html",true) // defaultSuccessUrl 和 successHandler配置到一块只会有最后面的有效
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)       // 自定义的登录接口
                .successHandler(myAuthenticationSuccessHandler)                                 // 认证成功回调
                .failureHandler(myAuthenticationFailureHandler);                           // 认证失败回调
    }

}
