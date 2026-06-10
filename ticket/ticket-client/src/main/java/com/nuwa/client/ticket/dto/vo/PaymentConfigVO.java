package com.nuwa.client.ticket.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商户App支付参数信息
 *
 * @author huyonghack@163.com
 * @since 2022-01-05
 */
@Data
public class PaymentConfigVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("渠道类型 douyin_mini zzz_gateway")
    private String channelType;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("支付密钥")
    private String salt;

    @ApiModelProperty("渠道商户号")
    private String channelMchId;

    @ApiModelProperty("商户clientId")
    private Long mchClientId;

}
