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
@ApiModel(value = "创建跳转第三方登录用户-命令")
public class BindAppJumpUrlParam extends NuwaCommand {

    @ApiModelProperty(value = "商户应用id", required = true)
    @NotBlank(message = "商户应用id不能为空")
    private Long merchantAppId;

    @ApiModelProperty(value = "账号名", required = true)
    @NotBlank(message = "账号名不能为空")
    private String username;

    @ApiModelProperty(value = "跳转的地址")
    private String jumpUrl;

}
