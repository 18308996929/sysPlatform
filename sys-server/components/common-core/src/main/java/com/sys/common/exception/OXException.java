package com.sys.common.exception;


import com.sys.common.constants.ErrorCode;

/**
 * 基础错误异常
 *
 * @author admin
 */
public class OXException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = ErrorCode.ERROR.getCode();

    public OXException() {

    }

    public OXException(String msg) {
        super(msg);
    }

    public OXException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public OXException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
