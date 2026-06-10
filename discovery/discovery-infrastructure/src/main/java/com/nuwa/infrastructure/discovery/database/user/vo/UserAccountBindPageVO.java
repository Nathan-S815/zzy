package com.nuwa.infrastructure.discovery.database.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
public class UserAccountBindPageVO {
    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("达人平台用户id")
    private String accountId;

    @ApiModelProperty("认证平台 douyin")
    private String channelCode;

    @ApiModelProperty("昵称")
    private String nick;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;

    @ApiModelProperty("创建时间IM")
    private Date createTime;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("uid")
    private String uid;

    @ApiModelProperty("手机号")
    private String userPhone;
}
