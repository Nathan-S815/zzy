package com.nuwa.attract.start.api.controller.auth.param;

import javax.validation.constraints.NotBlank;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改用户信息param
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.auth.param:ModifyUserInfoParam.java,v1.0.0 2022-09-07 10:46:14
 * nanHuang Exp $
 */
@Data
@ApiModel(value = "修改用户信息param")
@EqualsAndHashCode(callSuper = true)
public class ModifyPasswordParam extends NuwaCommand {
    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "旧密码不能为空")
    private String  lastPassword;
    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    private String  nextPassword;
}
