package com.nuwa.infrastructure.discovery.database.user.vo;

import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MemberVO {

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户头像")
    private String userImg;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("达人积分")
    private Integer integral;

    @ApiModelProperty("达人等级")
    private Integer userLevelId;

    @ApiModelProperty("达人等级")
    private String levelName;

    @ApiModelProperty("达人认证标签")
    private List<MemberTag> tagList;

    @ApiModelProperty("状态 0为未提交")
    private Integer recertificationStatus;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("达人地区")
    private String region;

    @ApiModelProperty("mchId")
    private String mchId;
}
