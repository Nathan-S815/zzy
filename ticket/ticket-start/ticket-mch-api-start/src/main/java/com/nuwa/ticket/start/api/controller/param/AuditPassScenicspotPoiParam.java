package com.nuwa.ticket.start.api.controller.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AuditPassScenicspotPoiParam POI审核通过")
public class AuditPassScenicspotPoiParam extends NuwaCommand {
    @ApiModelProperty(value = "Id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
