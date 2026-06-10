package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CheckUserTicketPrizeStatusParam 检测用户任务门票权益认领状态")
public class CheckUserTicketPrizeStatusParam {
    @ApiModelProperty("任务Id")
    private Long taskId;
}
