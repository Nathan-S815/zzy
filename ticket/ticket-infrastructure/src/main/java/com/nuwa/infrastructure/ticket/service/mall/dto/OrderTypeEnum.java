package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Getter;

/**
 * 视频支付状态【支付状态(1000待支付,1001已支付,1002已取消)】
 */
@Getter
public enum OrderTypeEnum {

    /**
     * 直销
     */
    DIRECT_SELLING(10, "直销");

    private Integer code;

    private String name;

    OrderTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
