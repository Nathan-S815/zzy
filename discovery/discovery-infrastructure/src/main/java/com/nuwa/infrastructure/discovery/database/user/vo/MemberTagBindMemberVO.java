package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberTagBindMemberVO {

    private Long id;

    @ApiModelProperty("达人id")
    private Long memberId;

    @ApiModelProperty("达人标签id")
    private Long memberTagId;

    @ApiModelProperty("达人名称")
    private String memberName;

    @ApiModelProperty("达人头像")
    private String img;
}
