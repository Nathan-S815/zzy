package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Getter;

/**
 * 视频支付状态【支付状态(1000待支付,1001已支付,1002已取消)】
 */
@Getter
public enum PayTypeEnum {

    /**
     * 直销
     */
    ONLINE(10, "在线支付");

    private Integer code;

    private String name;

    PayTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
