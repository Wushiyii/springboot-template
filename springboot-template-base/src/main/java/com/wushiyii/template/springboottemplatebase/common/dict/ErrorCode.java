package com.wushiyii.template.springboottemplatebase.common.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 */
@Getter
@AllArgsConstructor
public enum  ErrorCode {

    /**
     * 400错误，客户端传参错误用该code表示
     */
    BAD_REQUEST_ERROR(400, "Bad Request"),
    /**
     * 500错误，服务器错误用该code表示
     */
    SERVICE_INTERNAL_ERROR(500, "Service Internal Error"),

    /**
     * HTTP请求第三方出错
     */
    ERR_HTTP_CLIENT(3000, "请求第三方错误");

    private int code;
    private String desc;
}
