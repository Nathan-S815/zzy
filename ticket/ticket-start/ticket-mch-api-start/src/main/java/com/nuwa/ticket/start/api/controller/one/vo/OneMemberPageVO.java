package com.nuwa.ticket.start.api.controller.one.vo;

import com.nuwa.infrastructure.ticket.database.one.entity.OneMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OneMemberPageVO extends OneMember {
    @ApiModelProperty("用户中心id")
    private Long userCenterId;

    @ApiModelProperty("用户身份证号码")
    private String userIdCard;
}
