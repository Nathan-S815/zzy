package com.nuwa.ticket.start.api.controller.payconfig.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "修改微信小程序-商户")
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyWeiXinMiniMerchantParam extends NuwaCommand {

    @ApiModelProperty("id")
    @NotNull
    private Long id;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String name;

    /**
     * 私钥
     */
    @ApiModelProperty("渠道商户私钥地址（http开头）")
    private String priKeyUrl;

    @ApiModelProperty("支付密钥")
    private String wxSecretKey;

}
