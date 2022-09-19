package com.sys.login.config;


import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/27 21:41
 * @Description:    Session超时失效
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public MyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
