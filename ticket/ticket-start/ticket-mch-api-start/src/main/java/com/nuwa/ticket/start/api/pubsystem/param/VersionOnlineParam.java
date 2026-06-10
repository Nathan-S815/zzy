package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VersionOnlineParam 小程序上架")
public class VersionOnlineParam extends NuwaCommand {
    @ApiModelProperty("id")
    private Long id;
}
