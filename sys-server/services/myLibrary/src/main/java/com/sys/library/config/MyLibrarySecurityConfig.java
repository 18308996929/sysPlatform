package com.sys.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/16 22:06
 * @description：
 */
@Configuration
public class MyLibrarySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()   // 由于使用的是JWT，我们这里不需要csrf
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离采用JWT 不需要sessio
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                ).permitAll()
                .and().authorizeRequests()
                .antMatchers("/authentication/require").anonymous()
                .and()
                .authorizeRequests()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .access("@rbacPermission.hasPermission(request, authentication)");

        // 禁用缓存
        http.headers().cacheControl();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin().loginPage("/authentication/require");  // 后台接口访问受限资源时

        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }
}
