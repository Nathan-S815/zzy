package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "子账号移除应用")
public class SubAccountRemovedAppParam extends NuwaCommand {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("应用id")
    private Long appId;
}
