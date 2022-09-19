package com.sys.common.model.userSecurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 15:05
 * @description：  用户信息以及对应的权限信息都封装在该对象中，loadUserByUsername方法的返回值。
 */
public class UserAuthDetails implements UserDetails,Serializable {
    private SysUser user;
    private List<SysMenu> menus;   // 封装了user的权限列表信息

//    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    private Collection<? extends GrantedAuthority> authorities;
    public UserAuthDetails() {
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public UserAuthDetails(SysUser user, List<SysMenu> perms) {
        this.user = user;
        this.menus = perms;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 封装权限信息
        return menus.stream().filter(menu -> menu.getPem() != null).map(menu -> {
            // 获取该user对应的权限 封装到GrantedAuthority对象中
            return new SimpleGrantedAuthority(menu.getPem());
        }).collect(Collectors.toList());
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
