package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "绑定账号并报名任务参数")
public class BindAccountAndApplyTaskParam {

    @ApiModelProperty("所属任务id")
    private Long taskId;

    @ApiModelProperty("认证平台编码 douyin")
    private String platCode;

    @ApiModelProperty("昵称")
    private String nick;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("uid")
    private String uid;

    @ApiModelProperty("微信号")
    private String weixinId;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("认证截图 传Id，不要传url,多个逗号隔开")
    private String pictures;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("达人平台用户id")
    private String accountId;

    @ApiModelProperty("达人简介")
    private String content;

    @ApiModelProperty("出生日期")
    private String birthday;

    @ApiModelProperty("第三方平台标签 多个之间用逗号隔开")
    private String thirdPartyTag;
}
