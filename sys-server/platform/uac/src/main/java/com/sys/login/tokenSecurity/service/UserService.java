package com.sys.login.tokenSecurity.service;

import com.sys.common.model.userSecurity.SysMenu;
import com.sys.common.model.userSecurity.SysUser;

import java.util.List;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 15:16
 * @description：
 */
public interface UserService {
    List<SysMenu> getPermsByUserId(Long userId);

    /**
     * 登录
     * @param user username & password
     * @return token
     */
    String login(SysUser user);
}
