package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "任务认领参数")
public class OtherPrizeTaskSubmitParam {
    private String content;
    private String pictures;
}
