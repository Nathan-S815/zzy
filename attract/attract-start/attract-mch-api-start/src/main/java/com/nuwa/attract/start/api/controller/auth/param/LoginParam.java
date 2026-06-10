package com.nuwa.attract.start.api.controller.auth.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.auth.param:LoginParam.java,v1.0.0 2022-09-06 14:40:53 nanHuang Exp $
 */
@Data
@ApiModel(value = "用户登录参数")
@EqualsAndHashCode(callSuper = true)
public class LoginParam extends NuwaCommand {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String  username;
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String  password;
    @ApiModelProperty(value = "账号类型 0-文旅局 1-景区 2-酒店 3-旅行社", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer accountType;
    @ApiModelProperty(value = "验证码uuid", required = true)
    @NotBlank(message = "验证码uuid不能为空")
    private String  uuid;
    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String  code;
}
