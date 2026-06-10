package com.nuwa.client.ticket.api.appconfig.param;

import lombok.Data;

/**
 * 获取商户小程序支付参数
 *
 * @author hy
 */
@Data
public class GetAppConfigByOutAppIdParam {
    private String outAppId;
    private Long mchId;
}
