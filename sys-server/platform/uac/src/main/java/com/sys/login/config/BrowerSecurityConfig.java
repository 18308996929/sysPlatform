package com.sys.login.config;

import com.sys.login.authentication.AuthorizeConfigManager;
import com.sys.login.authentication.sms.AbstractChannelSecurityConfig;
import com.sys.login.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.sys.login.properties.SecurityConstants;
import com.sys.login.properties.SecurityProperties;
import com.sys.login.tokenSecurity.JWTAuthenticationFilter;
import com.sys.login.tokenSecurity.RestAuthenticationEntryPoint;
import com.sys.login.tokenSecurity.RestfulAccessDeniedHandler;
import com.sys.login.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/17 22:10
 * @Description:
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrowerSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    LogoutSuccessHandler myLogoutSucessHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;


    /**
     * 配置TokenRepository
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);

        http
                    // .apply(validateCodeSecurityConfig)
                    // .and()
                     .apply(smsCodeAuthenticationSecurityConfig).and() // 前后端分离时, 这两个是基于sesssion的 会失效
                    .csrf().disable()   // 由于使用的是JWT，我们这里不需要csrf
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离采用JWT 不需要session
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/swagger-resources/**",
                            "/v2/api-docs/**"
                    )
                    .permitAll().and()
                    .authorizeRequests()
                    .antMatchers(
                            SecurityConstants.LOGIN_PROCESSING_URL_FORM, // 用户名密码登录  前后端分离登录接口 基于jwt
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                             SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,//用户名密码登录 前后端不分离 基于session
                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE)// 手机验证码登录
                    .permitAll().and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                    .permitAll()
                    .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                    .authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        http.logout().logoutSuccessHandler(myLogoutSucessHandler);
        //添加JWT过滤器 除已配置的其它请求都需经过此过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 认证 和 授权异常同一处理
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restfulAccessDeniedHandler);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("000");
        System.out.println(encode);

    }
}
