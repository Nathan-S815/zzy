package com.nuwa.ticket.start.api.controller.payconfig.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "创建微信小程序-商户")
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateWeixinMiniMerchantParam extends NuwaCommand {

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String name;

    /**
     * 密钥
     */
    @ApiModelProperty("商户密钥")
    private String secretKey;

    @ApiModelProperty("支付方式 小程序：weixin_mini")
    private String payType;

    @ApiModelProperty("支付渠道小程序appId")
    private String channelAppId;

    @ApiModelProperty("支付渠道商户id")
    private String channelMchId;

    @ApiModelProperty("服务商id")
    private String sysServiceProviderId;

    /**
     * 私钥
     */
    @ApiModelProperty("支付渠道商户私钥地址（http开头）")
    private String priKeyUrl;

    @ApiModelProperty("支付密钥")
    private String wxSecretKey;


    @ApiModelProperty("底座商户id")
    private Long mchId;

}
