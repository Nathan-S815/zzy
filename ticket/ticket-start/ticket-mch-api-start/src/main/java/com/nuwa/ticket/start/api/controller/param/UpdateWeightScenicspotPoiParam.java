package com.nuwa.ticket.start.api.controller.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UpdateWeightScenicspotPoiParam 修改权重")
public class UpdateWeightScenicspotPoiParam extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "权重", required = true)
    private Integer weight;

}
