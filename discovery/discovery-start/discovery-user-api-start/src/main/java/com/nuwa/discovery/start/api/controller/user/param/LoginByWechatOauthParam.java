package com.nuwa.discovery.start.api.controller.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginByWechatOauthParam 微信公众号授权登录
 *
 * @author hy
 * @date 2021/5/7 9:39
 * @since 1.0.0
 */
@Data
public class LoginByWechatOauthParam {

    @ApiModelProperty(value = "code", required = true)
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "appId", required = true)
    @NotBlank(message = "appId不能为空")
    private String appId;
}
