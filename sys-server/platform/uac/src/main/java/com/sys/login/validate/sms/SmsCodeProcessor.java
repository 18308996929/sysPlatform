package com.sys.login.validate.sms;

import com.alibaba.fastjson.JSON;
import com.sys.common.model.R;
import com.sys.login.properties.SecurityConstants;
import com.sys.login.validate.ValidateCode;
import com.sys.login.validate.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 14:27
 * @Description:
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        super.send(request,validateCode);
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
        HttpServletResponse response = request.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(validateCode.getCode());
        response.getWriter().flush();
    }

}