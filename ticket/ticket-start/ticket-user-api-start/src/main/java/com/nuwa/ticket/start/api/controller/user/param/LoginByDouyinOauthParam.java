package com.nuwa.ticket.start.api.controller.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * LoginByDouyinOauthParam 抖音小陈序登录
 *
 * @author hy
 * @date 2021/5/7 9:39
 * @since 1.0.0
 */
@Data
@ToString
public class LoginByDouyinOauthParam {
    @ApiModelProperty(value = "code", required = true)
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "appId", required = true)
    @NotBlank(message = "appId不能为空")
    private String appId;

    @ApiModelProperty(value = "mchId", required = true)
    @NotNull(message = "商户Id不能为空")
    private Long mchId;
}
