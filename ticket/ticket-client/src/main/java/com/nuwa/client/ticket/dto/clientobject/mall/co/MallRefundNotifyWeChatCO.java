package com.nuwa.client.ticket.dto.clientobject.mall.co;

import lombok.Data;

import java.io.Serializable;

/**
 * MallRefundNotifyCO
 *
 * @author hy
 * @date 2020/11/21 13:09
 * @since 1.0.0
 */
@Data
public class MallRefundNotifyWeChatCO implements Serializable {

    private String transactionId;

    private String outTradeNo;

    private String refundId;

    private String outRefundNo;

    private Integer totalFee;

    private Integer settlementTotalFee;

    private Integer refundFee;

    private Integer settlementRefundFee;

    private String refundStatus;

    private String successTime;

    private String refundRecvAccout;

    private String refundAccount;

    private String refundRequestSource;

}
