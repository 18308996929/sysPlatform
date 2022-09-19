package com.sys.library.config;

import com.sys.common.model.userSecurity.SysMenu;
import com.sys.common.model.userSecurity.UserAuthDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/31 16:04
 * @Description:   跟业务相关的权限配置
 */
@Component("rbacPermission")
public class RbacPermission {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if(principal instanceof UserAuthDetails) {
            // 读取用户所拥有的权限url
            List<SysMenu> menus = ((UserAuthDetails) principal).getMenus();

            for (SysMenu menu : menus) {
                String pem = menu.getPem();
                /**
                 * request.getRequestURL(); //获取全路径 获取的是请求路径中的ip和端口号
                 * request.getRequestURI(); //获取端口号之后的路径
                 * request.getContextPath(); //获取.yml配置中的获取上下文路径(context-path)的名称
                 * request.getServletPath(); //获取.yml配置中context-path之后的路径
                 */
                if(antPathMatcher.match(pem, request.getServletPath())) {
                    hasPermission = true;
                    break;
                }
            }

        }
        return hasPermission;
    }
}
