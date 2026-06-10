package com.nuwa.infrastructure.ticket.third.paychannel.douyin.req;

import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import lombok.Data;

/**
 * 抖音创建退款订单
 *
 * @author hy
 */
@Data
public class CreateRefundReq {
    private DouYinPayConfig config;
    private String appId;
    private String outOrderNo;
    private String outRefundNo;
    /**
     * 退款原因
     */
    private String reason;
    private Long totalAmount;
    private String subject;
    private String body;

    private String notifyUrl;

    private String thirdpartyId;

    private Integer disableMsg;

    /**
     * 是否为分账后退款，1-分账后退款；0-分账前退款。分账后退款会扣减可提现金额，请保证余额充足
     */
    private Integer allSettle;
}
