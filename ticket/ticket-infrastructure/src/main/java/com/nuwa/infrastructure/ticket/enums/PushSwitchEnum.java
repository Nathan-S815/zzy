package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 订单通知开关
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum PushSwitchEnum {
    /**
     * 开启:1;停止:0
     */
    on(1, "开启"),
    off(0, "关闭"),
    ;

    private final Integer code;
    private final String message;

    PushSwitchEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
