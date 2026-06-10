package com.nuwa.infrastructure.ticket.service.log.enums;


import lombok.Getter;

/**
 * 供应商接口日志类型
 *
 * @author hy
 * @date 2021/10/21
 */
@Getter
public enum SupplierLogTypeEnum {
    /**
     * 日志类型
     * 1:下单
     * 2:支付
     * 3:核销通知
     * 4:退款通知
     * 5:出票通知
     * 6:退款
     */
    created(1, "下单"),
    payment(2, "支付"),
    ticketNotice(3, "核销通知"),
    refundNotice(4, "退款通知"),
    ticketed(5, "出票通知"),
    cancel(6, "取消订单"),
    ;

    private final Integer code;

    private final String message;

    SupplierLogTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SupplierLogTypeEnum getByCode(Integer code) {
        for (SupplierLogTypeEnum typeEnum : SupplierLogTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
