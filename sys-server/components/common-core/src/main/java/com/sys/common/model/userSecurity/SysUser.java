package com.sys.common.model.userSecurity;

import lombok.Data;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/17 14:34
 * @description：
 */
@Data
public class SysUser {
    Long id;
    String userName;
    String userPassword;
    String userPhone;
    String code;
}
