package com.sys.login.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/16 22:56
 * @description：
 */
@Data
public class SysUserRole {


    /**
     * 用户ID
     */
    private Long roleId;

    private Long userId;
    /**
     * 登录名
     */
    private String roleName;

}
