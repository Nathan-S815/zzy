package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberDataVO {

    @ApiModelProperty("达人数量")
    private Integer memberCount;

    @ApiModelProperty("任务接取数量")
    private Integer taskApplyCount;

    @ApiModelProperty("本月新增达人数")
    private Integer memberIncrCount;

    @ApiModelProperty("粉丝数")
    private Long fansCount;
}
