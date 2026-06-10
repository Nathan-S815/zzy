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
public class ResetMerchantSecretKeyParam extends NuwaCommand {

    @ApiModelProperty("id")
    @NotNull
    private Long id;

    /**
     * 商户名称
     */
    @ApiModelProperty("密钥")
    private String secretKey;

}
