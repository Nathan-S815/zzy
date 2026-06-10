package com.nuwa.ticket.start.dispatch.controller.notify.b2b.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * B2b 退款回调参数
 *
 * @author hy
 */
@Data
@ToString
public class B2bRefundNoticeModel {

    @JsonProperty(value = "OrderId")
    private String orderId;

    /**
     * 供应商申请流水号
     */
    @JsonProperty(value = "RefundApplyNo")
    private String refundApplyNo;

    /**
     * 1 通过 =2 不通过
     */
    @JsonProperty(value = "RefundStatus")
    private Integer refundStatus;

    @JsonProperty(value = "RefundReason")
    private String refundReason;

}
