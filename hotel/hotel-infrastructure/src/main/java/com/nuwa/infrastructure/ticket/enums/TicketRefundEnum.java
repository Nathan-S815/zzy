package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 退款状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum TicketRefundEnum {
    /**
     * 创建:1  退款中:2  已退款:3  退款失败:4
     */
    created(1, "创建"),
    refunding(2, "退款中"),
    refunded(3, "已退款"),
    refundFailed(4, "退款失败"),
    ;

    private final Integer code;
    private final String message;

    TicketRefundEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
