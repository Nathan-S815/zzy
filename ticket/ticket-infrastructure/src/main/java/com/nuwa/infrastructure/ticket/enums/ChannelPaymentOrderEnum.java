package com.nuwa.infrastructure.ticket.enums;


import lombok.Getter;

/**
 * 支付渠道订单状态
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum ChannelPaymentOrderEnum {
    /**
     * 创建:0;
     * 待支付:1;
     * 已支付:2;
     * 已关闭:3;
     * 失败:4
     */
    created(0, "创建"),
    waitPay(1, "待支付"),
    paid(2, "已支付"),
    closed(3, "已关闭"),
    failed(4, "待支付"),
    ;

    private final Integer code;
    private final String message;

    ChannelPaymentOrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
