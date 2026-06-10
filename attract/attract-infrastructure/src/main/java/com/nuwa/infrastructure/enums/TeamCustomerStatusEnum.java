package com.nuwa.infrastructure.enums;

import lombok.Getter;

/**
 * @Author: WangXh
 * @DateTime: 2022/10/26
 * @Description: 团队游客通用状态
 */
@Getter
public enum TeamCustomerStatusEnum {

    NORMAL(0, "正常"),
    DELETE(1, "已删除");

    private final Integer code;
    private final String message;

    TeamCustomerStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
