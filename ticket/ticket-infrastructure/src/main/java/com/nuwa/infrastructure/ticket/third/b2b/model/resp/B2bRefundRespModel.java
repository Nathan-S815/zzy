package com.nuwa.infrastructure.ticket.third.b2b.model.resp;

import lombok.Data;

/**
 * 退款响应对象
 *
 * @author hy
 */
@Data
public class B2bRefundRespModel {
    private String applyId;
    /**
     * 0 等待审核， 1 同意， 2拒绝
     */
    private String refundStatus;
}
