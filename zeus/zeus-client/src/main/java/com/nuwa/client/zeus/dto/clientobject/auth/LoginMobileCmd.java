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
@ApiModel(value = "手机号登录命令")
public class LoginMobileCmd extends NuwaCommand {

    @ApiModelProperty(value = "域名", required = true)
    private String domain;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "code", required = true)
    private String code;

}
