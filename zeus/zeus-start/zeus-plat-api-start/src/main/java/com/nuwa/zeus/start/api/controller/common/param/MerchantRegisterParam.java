package com.nuwa.zeus.start.api.controller.common.param;

import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * MerchantRegisterParam 商户注册
 *
 * @author hy
 * @date 2021/6/8 20:08
 * @since 1.0.0
 */
@Data
@ApiModel(value = "商户注册")
public class MerchantRegisterParam {

    private CreateMerchantCmd cmd;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;
}
