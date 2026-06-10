package com.nuwa.infrastructure.ticket.third.paychannel.douyin.req;

import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import lombok.Data;

/**
 * 抖音创建订单
 *
 * @author hy
 */
@Data
public class Code2SessionReq {
    private String appId;
    private String secret;
    private String code;
}
