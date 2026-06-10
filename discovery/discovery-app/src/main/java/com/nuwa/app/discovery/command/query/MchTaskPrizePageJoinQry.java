package com.nuwa.app.discovery.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
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
@ApiModel(value = "商户端查看任务权益记录分页PageQry")
public class MchTaskPrizePageJoinQry extends NuwaPageQry {

    @ApiModelProperty(value = "任务id")
    private Long taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "申领开始时间")
    private Date createTimeBegin;

    @ApiModelProperty(value = "申领结束时间")
    private Date createTimeEnd;

    @ApiModelProperty(value = "状态 1:待认领 2:已认领，待发放 3:已发放")
    private Integer status;

    @ApiModelProperty(value = "权益类型Id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.PRIZE_TYPE_ID)
    private Long prizeTypeId;

    @ApiModelProperty(value = "申领人")
    private String nick;

    @ApiModelProperty(value = "申领人Id")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.ACCOUNT_ID)
    private String accountId;

    @ApiModelProperty(value = "微信号")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.WEIXIN_ID)
    private String weixinId;

    @ApiModelProperty(value = "联系电话")
    private String userPhone;

    @ApiModelProperty(value = "达人id")
    private Long userId;
}
