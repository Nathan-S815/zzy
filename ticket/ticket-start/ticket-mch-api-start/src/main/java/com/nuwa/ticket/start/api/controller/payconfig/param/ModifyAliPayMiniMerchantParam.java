package com.nuwa.ticket.start.api.controller.payconfig.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "修改支付宝小程序-商户")
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyAliPayMiniMerchantParam extends NuwaCommand {

    @ApiModelProperty("id")
    @NotNull
    private Long id;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String name;

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
