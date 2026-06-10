package com.nuwa.infrastructure.ticket.third.b2b.req;

import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bPaymentReqModel;
import lombok.Data;

/**
 * B2B支付
 *
 * @author hy
 */
@Data
public class B2bPaymentReq {

    private B2bConfigModel config;

    private B2bPaymentReqModel model;
}
