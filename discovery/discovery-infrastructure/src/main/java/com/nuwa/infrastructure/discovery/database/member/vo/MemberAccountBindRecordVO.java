package com.nuwa.infrastructure.discovery.database.member.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MemberAccountBindRecordVO {

    @ApiModelProperty("id，主键，自动增长")
    private Long id;

    @ApiModelProperty("达人账号绑定记录id")
    private Long memberAccountBindId;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("达人平台用户id")
    private String accountId;

    @ApiModelProperty("认证平台 douyin|xiaohognshu")
    private String channelCode;

    @ApiModelProperty("昵称")
    private String nick;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("用户生日")
    private String birthday;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;

    @ApiModelProperty("过期时间")
    private Date expiresTime;

    @ApiModelProperty("文字信息")
    private String content;

    @ApiModelProperty("图片信息")
    @JsonSerialize(using = MaterialJson.class)
    private String pictures;

    @ApiModelProperty("第三方平台标签 多个之间用逗号隔开")
    private String thirdPartyTag;

    @ApiModelProperty("拒绝原因")
    private String refuseReason;

    @ApiModelProperty("图片（审核未通过时该字段才用得上）")
    @JsonSerialize(using = MaterialJson.class)
    private String refusePictures;
}
