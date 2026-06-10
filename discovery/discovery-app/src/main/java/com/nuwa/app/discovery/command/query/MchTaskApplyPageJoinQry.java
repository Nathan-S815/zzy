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
public class MchTaskApplyPageJoinQry extends NuwaPageQry {

    @ApiModelProperty(value = "任务id")
    private Long taskId;

    @ApiModelProperty("达人用户id")
    private String accountId;

    @ApiModelProperty("达人id")
    private String userId;

    @ApiModelProperty("达人昵称")
    private String nick;

    @ApiModelProperty("微信昵称")
    private String userNike;

    @ApiModelProperty("开始报名时间")
    private Date createTimeBegin;

    @ApiModelProperty("结束报名时间")
    private Date createTimeEnd;

    @ApiModelProperty("达人等级")
    private String level;

    @ApiModelProperty("达人性别 1:男 0:女")
    private Integer sex;

    @ApiModelProperty("达人地域")
    private String region;
}
