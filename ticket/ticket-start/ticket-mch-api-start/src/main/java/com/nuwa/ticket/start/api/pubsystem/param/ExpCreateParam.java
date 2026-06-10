package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VersionRollbackParam 小程序生成体验版")
public class ExpCreateParam extends NuwaCommand {
    @ApiModelProperty("id")
    private Long id;
}
