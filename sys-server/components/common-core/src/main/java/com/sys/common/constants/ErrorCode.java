package com.sys.common.constants;

/**
 * 自定义返回码
 *
 * @author admin
 */

public enum ErrorCode {
    /**
     * 成功
     */
    OK(0, "success"),
    FAIL(1000, "fail"),
    ALERT(1001, "alert"),
    ERROR(5000, "error"),
    EMPTY(1002,"");

    private int code;
    private String message;

    ErrorCode() {
    }

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getResultEnum(int code) {
        for (ErrorCode type : ErrorCode.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return EMPTY;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
