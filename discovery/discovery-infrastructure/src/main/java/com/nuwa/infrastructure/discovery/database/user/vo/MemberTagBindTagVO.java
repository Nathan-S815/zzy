package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberTagBindTagVO {

    private Long id;

    @ApiModelProperty("达人id")
    private Long memberId;

    @ApiModelProperty("达人标签id")
    private Long memberTagId;

    @ApiModelProperty("达人标签名称")
    private String memberTagName;

    @ApiModelProperty("是否选中标记 0：否 1：是")
    private Integer isCheck;
}
