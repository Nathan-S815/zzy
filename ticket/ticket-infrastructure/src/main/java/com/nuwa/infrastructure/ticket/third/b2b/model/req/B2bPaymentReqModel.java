package com.nuwa.infrastructure.ticket.third.b2b.model.req;

import lombok.Data;

import java.util.List;

/**
 * @author hy
 */
@Data
public class B2bPaymentReqModel {

    /**
     * 合作商Id
     */
    private String partnerId;

    /**
     * 订单id
     */
    private String orderId;
}
