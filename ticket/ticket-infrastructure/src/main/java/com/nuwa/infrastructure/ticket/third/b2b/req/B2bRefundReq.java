package com.nuwa.infrastructure.ticket.third.b2b.req;

import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bPaymentReqModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bRefundReqModel;
import lombok.Data;

/**
 * B2B退款
 *
 * @author hy
 */
@Data
public class B2bRefundReq {

    private B2bConfigModel config;

    private B2bRefundReqModel model;
}
