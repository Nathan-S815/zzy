package com.nuwa.zeus.start.api.controller.plat.app.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "删除应用版本")
public class RemovedAppUpdateLogParam extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    private List<Long> id;
}
