package com.nuwa.ticket.start.api.pubsystem.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AuditCancelParam 小程序撤销审核")
public class AuditCancelParam extends NuwaCommand {
    @ApiModelProperty("id")
    @NotNull
    private Long id;
}
