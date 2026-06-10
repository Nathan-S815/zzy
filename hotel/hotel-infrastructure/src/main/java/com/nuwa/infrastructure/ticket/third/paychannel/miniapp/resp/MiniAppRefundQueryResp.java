package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import lombok.Data;

/**
 * WxMiniOrderCreateResp
 *
 * @author hy
 * @date 2020/10/29 16:40
 * @since 1.0.0
 */
@Data
public class MiniAppRefundQueryResp {
    private Integer mchId;
    /**
     * "REFUND_FAILED", "退款失败"
     */
    private String status;
    private String statusDesc;
    private Long amount;
    private String platOrderNo;
    private String mchOrderNo;
    private String refundSuccessTime;
    private Long refundSurplusAmount;
    private String channelType;
    private String clearDate;
}
