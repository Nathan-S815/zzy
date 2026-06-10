package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AppMembersDeleteParam 应用删除成员")
public class AppMembersDeleteParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "查询的成员角色类型", allowableValues = "DEVELOPER：开发者;" +
            "EXPERIENCER：体验者")
    private String role;

    @ApiModelProperty(value = "支付宝账户唯一标识", allowableValues = "被删除成员的支付宝账户唯一标识，以2088开头")
    private String userId;
}
