package com.nuwa.ticket.start.api.controller.payconfig.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "创建支付宝小程序-商户")
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateAliPayMiniMerchantParam extends NuwaCommand {

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String name;

    /**
     * 商户id
     */
    @ApiModelProperty("底座商户id")
    private Long mchId;

    @ApiModelProperty("支付方式 小程序：alipay_mini 服务商小程序alipay_mini_service  小程序模板:alipay_mini_template")
    private String payType;

    @ApiModelProperty("支付渠道小程序appId")
    private String channelAppId;

    @ApiModelProperty("三方应用AppId")
    private String templateAppId;

    @ApiModelProperty("服务商id")
    private String sysServiceProviderId;

    /**
     * 公钥地址
     */
    @ApiModelProperty("支付渠道商户公钥地址（http开头）")
    private String pubKeyUrl;

    /**
     * 私钥
     */
    @ApiModelProperty("支付渠道商户私钥地址（http开头）")
    private String priKeyUrl;

    /**
     * 支付宝公钥
     */
    @ApiModelProperty("支付宝公钥（http开头）")
    private String aliPayPubKeyUrl;

}
