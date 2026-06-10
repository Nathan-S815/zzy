package com.nuwa.app.discovery.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户端查看报名记录分页PageQry")
public class UserBindAccountPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("达人用户id")
    private String accountId;

    @ApiModelProperty("达人昵称")
    private String nick;

    @ApiModelProperty("开始时间")
    private Date createTimeBegin;

    @ApiModelProperty("结束时间")
    private Date createTimeEnd;

    @ApiModelProperty("达人等级")
    private String level;

    @ApiModelProperty("达人性别 1:男 0:女")
    private Integer sex;

    @ApiModelProperty("达人地域")
    private String region;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("uId")
    private String uId;

    @ApiModelProperty("最小粉丝数")
    private Long  fansMin;

    @ApiModelProperty("最大粉丝数")
    private Long  fansMax;

    @ApiModelProperty("状态 0:已提交,待审核 1:绑定成功 2:绑定失败 3:已过期")
    private Integer status;
}
