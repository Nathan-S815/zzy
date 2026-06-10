package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "TopTaskParam 上热门参数")
public class TopTaskParam {

    @ApiModelProperty(value = "任务id", required = true)
    @NotNull(message = "id 不能为空")
    private Long id;
}
