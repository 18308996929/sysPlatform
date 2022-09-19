package com.sys.library.config;

import com.sys.common.model.userSecurity.UserAuthDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 13:52
 * @description：
 */
@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader:token}")
    private String tokenHeader; // 请求头名: token

    @Value("${jwt.tokenHead:Bear}")
    private String tokenHead;   // jwt中的header部分

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 获取请求头中的token
        String authToken = request.getHeader(this.tokenHeader);

        if (authToken != null && authToken.startsWith(this.tokenHead)) {
            // 从token中获取username
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            log.info("checking username:{}", username);
            // 校验redis是否登录
            UserAuthDetails userAuthDetails = (UserAuthDetails) redisTemplate.opsForValue().get(authToken);
            if (null == userAuthDetails) {
                throw  new RuntimeException("请先登录");
            }
            if (jwtTokenUtil.validateToken(authToken, userAuthDetails)) {
                log.info("authenticated user:{} 通过", username);
                // 封装数据放入authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userAuthDetails, null, userAuthDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user:{}", username);
                // 存储 authentication对象 至SecurityContextHolder上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                throw  new RuntimeException("请重新登录");
            }
        }
        chain.doFilter(request, response);
    }
}
