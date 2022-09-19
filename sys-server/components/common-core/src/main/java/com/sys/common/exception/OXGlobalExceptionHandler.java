package com.sys.common.exception;

import com.google.common.html.HtmlEscapers;
import com.sys.common.constants.ErrorCode;
import com.sys.common.model.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理器
 *
 * @author oxhainan
 * @date 2017/7/3
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class OXGlobalExceptionHandler {


    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public static ResultBody authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, HtmlEscapers.htmlEscaper().escape(request.getRequestURI()));
        response.setStatus(resultBody.getHttpStatus());
        return resultBody;
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2Exception.class, InvalidTokenException.class})
    public static ResultBody oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, HtmlEscapers.htmlEscaper().escape(request.getRequestURI()));
        response.setStatus(resultBody.getHttpStatus());
        return resultBody;
    }

    /**
     * 自定义异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OXException.class})
    public static ResultBody openException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, HtmlEscapers.htmlEscaper().escape(request.getRequestURI()));
        response.setStatus(resultBody.getHttpStatus());
        return resultBody;
    }

    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public static ResultBody exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultBody resultBody = resolveException(ex, HtmlEscapers.htmlEscaper().escape(request.getRequestURI()));
        response.setStatus(resultBody.getHttpStatus());
        return resultBody;
    }

    public static ResultBody resolveException(Exception ex, String path) {
        ErrorCode code = ErrorCode.ERROR;
        Integer defineCode = null;
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = ex.getMessage();
        String superClassName = ex.getClass().getSuperclass().getName();
        String className = ex.getClass().getName();
        Object data = null;
        return buildBody(ex, code, path, httpStatus,defineCode,message,data);
    }

    /**
     * 构建返回结果对象
     *
     * @param exception
     * @return
     */
    private static ResultBody buildBody(Exception exception, ErrorCode resultCode,
                                        String path, int httpStatus, Integer defineCode, String defineMessage, Object data) {
        ResultBody resultBody;
        if (defineCode == null) {
//            resultBody = ResultBody.failed(resultCode).httpStatus(httpStatus);
            resultBody = ResultBody.define(resultCode.getCode(),exception.getMessage());
        }else {
            resultBody = ResultBody.define(defineCode,defineMessage).httpStatus(httpStatus);
        }
        log.error("OXGlobalExceptionHandler path:{}, resultBody:{},exception: ", path, resultBody, exception);
        resultBody.data(data);
        return resultBody;
    }

}
