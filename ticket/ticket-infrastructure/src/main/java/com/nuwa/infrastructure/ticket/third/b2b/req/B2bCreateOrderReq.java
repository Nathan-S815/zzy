package com.nuwa.infrastructure.ticket.third.b2b.req;

import com.nuwa.infrastructure.ticket.third.b2b.model.B2bConfigModel;
import com.nuwa.infrastructure.ticket.third.b2b.model.req.B2bCreateOrderReqModel;
import lombok.Data;

/**
 * B2B创建订单
 *
 * @author hy
 */
@Data
public class B2bCreateOrderReq {

    private B2bCreateOrderReqModel model;

    private B2bConfigModel config;
}
