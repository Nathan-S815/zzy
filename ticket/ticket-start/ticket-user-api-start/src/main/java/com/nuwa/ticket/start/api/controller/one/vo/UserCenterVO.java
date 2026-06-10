package com.nuwa.ticket.start.api.controller.one.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserCenterVO {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("用户虚拟头像")
    @JsonSerialize(using = MaterialJson.class)
    private String userImg;

    @ApiModelProperty("用户身份证ID")
    private String userIdCard;

    @ApiModelProperty("用户真实姓名")
    private String userRealName;

    @ApiModelProperty("是否实名认证 yes|no")
    private String identityAuth;

    @ApiModelProperty("拥有的身份")
    private List<String> identityNameList;
}
