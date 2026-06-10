package com.nuwa.ticket.start.api.controller.user.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * LoginByAlipayParam 支付宝登录命令参数
 *
 * @author hy
 * @date 2021/4/30 13:31
 * @since 1.0.0
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "支付宝登录命令参数")
@Data
public class LoginByAlipayParam extends NuwaCommand {

    @ApiModelProperty(value = "code")
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "ip", hidden = true)
    private String ip;

    @ApiModelProperty(value = "appId", hidden = true)
    private String appId;
}
