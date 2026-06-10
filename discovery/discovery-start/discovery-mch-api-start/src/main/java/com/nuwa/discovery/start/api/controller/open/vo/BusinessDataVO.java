package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessDataVO {

    @ApiModelProperty("商家数量")
    private Integer businessCount;

    @ApiModelProperty("任务数量")
    private Integer taskCount;

    @ApiModelProperty("本月新增商户数")
    private Integer businessIncrCount;

    @ApiModelProperty("任务完成率")
    private Double taskPercentageComplete;
}
