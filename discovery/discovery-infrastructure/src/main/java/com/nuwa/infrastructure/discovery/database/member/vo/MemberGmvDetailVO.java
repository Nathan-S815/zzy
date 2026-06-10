package com.nuwa.infrastructure.discovery.database.member.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/6
 * @Description: TODO
 */
@Data
public class MemberGmvDetailVO {

    @ApiModelProperty("id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("用户头像")
    private String userImg;

    @ApiModelProperty("达人GMV")
    private BigDecimal gmv;
}
