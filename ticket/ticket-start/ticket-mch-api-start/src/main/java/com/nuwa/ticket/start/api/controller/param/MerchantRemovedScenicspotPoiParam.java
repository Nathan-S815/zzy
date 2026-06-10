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
@ApiModel(value = "MerchantRemovedScenicspotPoiParam 移除景区POI")
public class MerchantRemovedScenicspotPoiParam extends NuwaCommand {

    @ApiModelProperty(value = "ids", required = true)
    @NotNull(message = "ids不能为空")
    @Size(min = 0, message = "ids不能为空")
    private List<Long> ids;
}
