package com.nuwa.discovery.start.api.controller.task.param;

import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "StartTaskParam 开始/结束任务参数")
public class StartTaskParam {

    @ApiModelProperty(value = "任务id", required = true)
    @NotNull(message = "id 不能为空")
    private Long id;
}
