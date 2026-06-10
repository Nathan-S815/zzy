package com.nuwa.infrastructure.ticket.enums;

import lombok.Getter;

/**
 * 视频支付状态【支付状态(1000待支付,1001已支付,1002已取消)】
 */
@Getter
public enum PaymentStatusEnum {

    /**
     * 待支付
     */
    CREATE(10, "待支付"),
    /**
     * 已支付
     */
    PAYMENT_SUCCESS(20, "已支付"),
    /**
     * 无效
     */
    CANCELED(30, "已取消"),

    /**
     * 已删除
     */
    DELETE(40, "已过期"),

    //10待支付 11代发货 12待收货 13已完成 14退款审核 15退款中 16已取消 17已退款 18退款失败
    /**
     * 待支付
     */
    WAIT_FOR_PAY(10, "待支付"),

    /**
     * 待发货
     */
    WAIT_FOR_SEND(11, "待发货"),

    /**
     * 待收货
     */
    WAIT_FOR_SIGN(12, "待收货"),

    /**
     * 已完成
     */
    FINISH(13, "已完成"),

    /**
     * 退款审核
     */
    REFUND_EXAMINE(14, "退款审核"),

    /**
     * 退款审核
     */
    REFUNDING(15, "退款中"),

    /**
     * 已取消
     */
    CANCLED_ORDER(16, "已取消"),

    /**
     * 已退款
     */
    REFUNDED(17, "已退款"),

    /**
     * 退款失败
     */
    FAILED_REFUND(18, "退款失败");


    private Integer code;

    private String name;

    PaymentStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
