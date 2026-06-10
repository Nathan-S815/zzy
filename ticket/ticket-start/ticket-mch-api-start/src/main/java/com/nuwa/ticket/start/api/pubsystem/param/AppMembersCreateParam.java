package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppMembersCreateParam 应用添加成员")
public class AppMembersCreateParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "查询的成员角色类型", allowableValues = "DEVELOPER：开发者;" +
            "EXPERIENCER：体验者")
    private String role;

    @ApiModelProperty(value = "支付宝登录账号", allowableValues = "")
    private String logonId;
}
