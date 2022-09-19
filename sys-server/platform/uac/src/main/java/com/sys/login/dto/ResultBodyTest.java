package com.sys.login.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.sys.common.constants.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author
 */
@Data
public class ResultBodyTest<T> implements Serializable {
    private static final long serialVersionUID = -6190689122701100762L;

    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码:0-请求处理成功")
    private int code = 0;

    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;


}
