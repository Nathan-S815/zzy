package com.nuwa.infrastructure.discovery.database.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberTagCountVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("标签绑定达人数量")
    private Integer memberCount;
}
