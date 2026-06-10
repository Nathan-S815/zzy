package com.nuwa.infrastructure.discovery.database.user.vo;

import com.nuwa.infrastructure.discovery.database.member.entity.MemberTag;
import com.nuwa.infrastructure.discovery.database.member.vo.MemberTagVO;
import com.nuwa.infrastructure.discovery.database.user.vo.base.MemberPageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberMerchantPageVO extends MemberPageBase {

    @ApiModelProperty("用户第三方平台昵称")
    private String nick;

    @ApiModelProperty("用户第三方平台账号")
    private String accountId;

    @ApiModelProperty("达人等级")
    private Integer userLevelId;

    @ApiModelProperty("达人等级")
    private String levelName;

    @ApiModelProperty("达人等级积分")
    private Integer integral;

    @ApiModelProperty("达人gmv 单位：分")
    private Long gmv;

    @ApiModelProperty("达人认证标签")
    private List<MemberTag> tagList;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("mchId")
    private String mchId;

    @ApiModelProperty("用户头像")
    private String userImg;
}
