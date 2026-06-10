package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "达人排名对象")
public class MemberRankDataVO {

    @ApiModelProperty("粉丝数")
    private Integer fansCount;


    @ApiModelProperty("本月新增达人数")
    private String userNike;
}
