package com.nuwa.infrastructure.discovery.enums;

import lombok.Getter;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/2
 * @Description: TODO
 */
@Getter
public enum SourceEnum {

    /**
     * 通用编码
     */
    PLATFORM(1, "平台"),

    MERCHANT(2, "商户"),

    CONSUMER(3, "用户");

    private final Integer code;
    private final String message;

    SourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
