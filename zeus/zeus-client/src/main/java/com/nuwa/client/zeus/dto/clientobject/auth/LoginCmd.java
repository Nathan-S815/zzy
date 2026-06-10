package com.nuwa.client.zeus.dto.clientobject.auth;


import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "登录命令")
public class LoginCmd extends NuwaCommand {

    @ApiModelProperty(value = "域名", required = true)
    private String domain;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "uuid", required = true)
    private String uuid;

    @ApiModelProperty(value = "code", required = true)
    private String code;

    @ApiModelProperty(value = "登录平台(1总平台 2商户后台)", required = true)
    private Integer platType;
}
