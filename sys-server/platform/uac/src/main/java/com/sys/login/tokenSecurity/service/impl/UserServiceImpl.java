package com.sys.login.tokenSecurity.service.impl;

import com.sys.common.model.userSecurity.SysMenu;
import com.sys.common.model.userSecurity.SysUser;
import com.sys.common.model.userSecurity.UserAuthDetails;
import com.sys.common.utils.string.StringUtils;
import com.sys.login.mapper.UserMapper;
import com.sys.login.tokenSecurity.JwtTokenUtil;
import com.sys.login.tokenSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 15:17
 * @description：
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SysMenu> getPermsByUserId(Long userId) {
        return userMapper.getPermsByUserId(userId);
    }

    @Override
    public String login(SysUser user) {
        String token = null;
        String username = user.getUserName();
        String userPhone = user.getUserPhone(); // 手机号 和  用户名都可以作为登录凭证  但是只能有一个有值
        String password = user.getUserPassword();

        String code = (String) redisTemplate.opsForValue().get(user.getCode()); // 图片验证码 或 手机验证码

        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("验证码失效！！");
        }
        if (!code.equals(user.getCode())) {
            throw new RuntimeException("验证码错误！！");
        }

        // 将用户名和密码封装为一个Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 调用认证方法
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误！！");
        }

        UserAuthDetails userDetails = (UserAuthDetails) authenticate.getPrincipal();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,  userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token返回
        token = jwtTokenUtil.generateToken(userDetails);

        // 基本信息 token 存 redis
        redisTemplate.opsForValue().set(token, userDetails,60L,TimeUnit.MINUTES);
        return token;
    }
}
