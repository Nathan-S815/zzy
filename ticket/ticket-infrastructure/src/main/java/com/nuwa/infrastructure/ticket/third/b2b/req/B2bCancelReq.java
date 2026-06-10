package com.nuwa.infrastructure.ticket.third.b2b.req;

import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bPaymentReqModel;
import lombok.Data;

/**
 * B2b 取消接口
 *
 * @author hy
 */
@Data
public class B2bCancelReq {

    private B2bConfigModel config;

    /**
     * b2b订单号
     */
    private String orderId;
}
