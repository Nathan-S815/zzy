package com.nuwa.infrastructure.discovery.database.user.vo;

import com.nuwa.infrastructure.discovery.database.user.vo.base.MemberPageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MemberExaminePageVO extends MemberPageBase {

    @ApiModelProperty("用户第三方平台昵称")
    private String nick;

    @ApiModelProperty("用户第三方平台账号")
    private String accountId;

    @ApiModelProperty("粉丝数")
    private Long fansCount;

    @ApiModelProperty("用户所在地")
    private String region;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date updateTime;

    @ApiModelProperty("重新认证状态0：未提交 1：已认证 2：需重新认证 3：认证失败")
    private Integer recertificationStatus;

    @ApiModelProperty("mchId")
    private String mchId;

    @ApiModelProperty("三方账号等级")
    private String level;
}
