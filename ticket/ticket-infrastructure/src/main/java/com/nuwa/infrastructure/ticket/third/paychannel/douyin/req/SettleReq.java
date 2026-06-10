package com.nuwa.infrastructure.ticket.third.paychannel.douyin.req;

import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import lombok.Data;

/**
 * 抖音创建退款订单
 *
 * @author hy
 */
@Data
public class SettleReq {
    private DouYinPayConfig config;
    private String appId;
    private String outSettleNo;
    private String outOrderNo;
    private String settleDesc = "结算";
    private String settleParams;
    private String notifyUrl;
    /**
     * 第三方平台服务商 id，非服务商模式留空
     */
    private String thirdpartyId;
}
