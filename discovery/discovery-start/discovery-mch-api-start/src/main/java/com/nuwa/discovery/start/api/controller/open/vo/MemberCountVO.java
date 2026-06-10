package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "达人数量对象")
public class MemberCountVO {

    @ApiModelProperty("达人数量")
    private Integer memberCount;


    @ApiModelProperty("本月新增达人数")
    private Integer memberIncrCount;
}
