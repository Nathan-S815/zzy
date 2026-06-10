package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 上下架状态枚举
 *
 * @author hy
 * @date 2021/4/28 19:58
 * @since 1.0.0
 */
@Getter
public enum PublishStatusEnum {

    /**
     * 通用状态
     */
    OFF(0, "下架"),
    ON(1, "上架");

    private final Integer code;

    private final String message;

    PublishStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
