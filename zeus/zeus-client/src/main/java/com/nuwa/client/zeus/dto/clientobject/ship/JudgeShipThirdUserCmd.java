package com.nuwa.client.zeus.dto.clientobject.ship;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "判断是否已登录过-命令")
public class JudgeShipThirdUserCmd extends NuwaCommand {

    @ApiModelProperty(value = "商户ID", required = true)
    @NotBlank(message = "商户ID不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "商户应用id", required = true)
    @NotBlank(message = "商户应用id不能为空")
    private Long merchantAppId;
}
