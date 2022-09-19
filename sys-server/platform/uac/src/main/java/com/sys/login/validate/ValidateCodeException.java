package com.sys.login.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/21 21:11
 * @Description:
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
