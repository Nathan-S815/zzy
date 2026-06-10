package com.nuwa.ticket.start.api.controller.user.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "微信授权命令")
public class WeChatSignParam extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "AppId")
    private String appId;

    @ApiModelProperty(value = "商户ID")
    private Long mchId;

    @ApiModelProperty(value = "url")
    private String url;
}
