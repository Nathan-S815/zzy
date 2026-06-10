package com.nuwa.ticket.start.api.controller.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AuditRejectScenicspotPoiParam POI审核拒绝")
public class AuditRejectScenicspotPoiParam extends NuwaCommand {
    @ApiModelProperty(value = "Id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "拒绝原因", required = true)
    @NotBlank(message = "拒绝原因不能为空")
    private String reason;
}
