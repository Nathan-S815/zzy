package com.nuwa.client.ticket.util;


import lombok.Getter;

/**
 * @author hy
 */

@Getter
public enum ChannelResultCodeEnum {

    SUCCESS("SUCCESS", "成功"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知异常"),
    TIMEOUT_ERROR("TIMEOUT_ERROR", "请求超时"),
    BUILD_REQUEST_PARAMS_ERROR("BUILD_REQUEST_PARAMS_ERROR", "构建请求参数异常"),
    PARSE_RESPONSE_ERROR("Z403", "解析响应报文异常");

    private final String code;
    private final String message;

    ChannelResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
