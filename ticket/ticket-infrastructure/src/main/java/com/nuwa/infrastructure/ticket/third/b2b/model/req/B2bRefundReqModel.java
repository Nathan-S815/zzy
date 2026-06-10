package com.nuwa.infrastructure.ticket.third.b2b.model.req;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class B2bRefundReqModel {

    /**
     * 合作商Id
     */
    private String partnerId;

    /**
     * 订单id
     */
    private String orderId;

    private Integer refundCount;

    private String refundId;

    private String voucherCode;
}
