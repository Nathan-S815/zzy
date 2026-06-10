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
@ApiModel(value = "ModifyTaskWeightParam 修改任务/视频权重")
public class ModifyTaskWeightParam {

    @ApiModelProperty(value = "任务/视频id(与视频共用)", required = true)
    @NotNull(message = "id 不能为空")
    private Long id;

    @ApiModelProperty(value = "权重值", required = true)
    @NotNull(message = "id 不能为空")
    private Integer weight;
}
