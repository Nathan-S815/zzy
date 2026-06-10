package com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class CreateRefundResp {
    private String err_no;
    private String err_tips;

    /**
     * 担保交易服务端退款单号
     */
    private String refund_no;
}
