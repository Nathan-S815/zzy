package com.nuwa.zeus.start.api.controller.merchant.param;

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
@ApiModel(value = "获取当前用户已绑定三方用户列表")
public class GetCurrentThirdUserListParam extends NuwaCommand {

    @ApiModelProperty(value = "appId", required = true)
    @NotBlank(message = "appId")
    private Long appId;
}
