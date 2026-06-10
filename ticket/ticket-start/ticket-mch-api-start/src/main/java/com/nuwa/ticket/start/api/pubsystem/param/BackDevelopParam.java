package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BackDevelopParam 退回开发")
public class BackDevelopParam extends NuwaCommand {
    @ApiModelProperty("id")
    private Long id;
}
