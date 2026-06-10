package com.nuwa.infrastructure.ticket.third.paychannel.douyin.req;

import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import lombok.Data;

/**
 * 抖音创建订单
 *
 * @author hy
 */
@Data
public class CreateOrderReq {
    private DouYinPayConfig config;
    private String appId;
    private String outOrderNo;
    private Long totalAmount;
    private String subject;
    private String body;
    /**
     * 订单过期时间(秒); 最小 15 分钟，最大两天
     */
    private Integer validTime;

    private String notifyUrl;

    private String thirdpartyId;

    private Integer disableMsg;

    private String msgPage;
}
