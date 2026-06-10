package com.nuwa.ticket.start.api.controller.user.vo;

import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberPageVO extends Member {
    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("应用类型")
    private String appType;

    @ApiModelProperty("用户中心ID")
    private Integer centerUserId;

    @ApiModelProperty("用户中心手机")
    private String centerUserPhone;

    @ApiModelProperty("用户中心身份证")
    private String centerIdCard;

    @ApiModelProperty("用户中心姓名")
    private String centerRealName;
}
