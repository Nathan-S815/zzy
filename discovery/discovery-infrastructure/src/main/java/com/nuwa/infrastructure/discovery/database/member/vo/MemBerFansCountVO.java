package com.nuwa.infrastructure.discovery.database.member.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/6
 * @Description: 达人粉丝榜
 */
@Data
public class MemBerFansCountVO {

    @ApiModelProperty("id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户微信昵称")
    private String userWechatNick;

    @ApiModelProperty("用户抖音昵称")
    private String userDouYinNick;

    @ApiModelProperty("用户等级")
    private String level;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("注册时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("第三方平台标签 多个之间用逗号隔开")
    private String thirdPartyTag;
}
