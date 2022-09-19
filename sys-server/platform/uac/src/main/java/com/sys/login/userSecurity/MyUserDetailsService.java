package com.sys.login.userSecurity;

import com.sys.common.model.userSecurity.SysMenu;
import com.sys.common.model.userSecurity.SysUser;
import com.sys.common.model.userSecurity.UserAuthDetails;
import com.sys.login.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/17 22:33
 * @Description:
 */
@Component
public class MyUserDetailsService implements UserDetailsService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) { // 用户名 或手机号
        logger.info("用户的用户名: {}", username);
        // TODO 根据用户名，查找到对应的密码，与权限
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setUserPhone(username);
        SysUser user1 = userMapper.getUser(sysUser);

        if (null == user1) {
//            throw new RuntimeException("该用户不存在！");
            throw new UsernameNotFoundException(username + "该用户不存在！");
        }
        List<SysMenu> permsByUserId = userMapper.getPermsByUserId(user1.getId());

        // 参数分别是：用户名，密码，用户权限
//        User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(user1.getRoleName()));
        return new UserAuthDetails(user1, permsByUserId);
    }
}
