package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "GetTaskDetailById 获取任务详情参数")
public class GetTaskDetailById {

    private String id;
}
