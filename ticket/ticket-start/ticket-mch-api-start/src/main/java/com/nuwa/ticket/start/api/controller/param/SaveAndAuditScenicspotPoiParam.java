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
@ApiModel(value = "SaveAndAuditScenicspotPoiParam POI提交审核")
public class SaveAndAuditScenicspotPoiParam extends NuwaCommand {
    @ApiModelProperty(value = "Id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
