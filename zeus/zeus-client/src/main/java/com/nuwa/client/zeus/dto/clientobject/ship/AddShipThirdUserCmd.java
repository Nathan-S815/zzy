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
@ApiModel(value = "创建跳转第三方登录用户-命令")
public class AddShipThirdUserCmd extends NuwaCommand {

    @ApiModelProperty(value = "商户ID", required = true)
    @NotBlank(message = "商户ID不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "商户应用id", required = true)
    @NotBlank(message = "商户应用id不能为空")
    private Long merchantAppId;

    @ApiModelProperty(value = "账号名", required = true)
    @NotBlank(message = "账号名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "登录提交的url")
    private String loginSubmitUrl;

    @ApiModelProperty(value = "景区编码")
    private String scenicCode;

}
