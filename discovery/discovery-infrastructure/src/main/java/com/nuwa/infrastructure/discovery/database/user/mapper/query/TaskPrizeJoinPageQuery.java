package com.nuwa.infrastructure.discovery.database.user.mapper.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "用户任务权益分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskPrizeJoinPageQuery extends BaseJoinPagingQuery<TaskPrizeJoinPageQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.ID)
    private Long taskId;

    @ApiModelProperty(value = "创建者id")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.CREATE_BY_ID)
    private String createById;

    @ApiModelProperty(value = "用户权益id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.ID)
    private Long userPrizeId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.USER_ID)
    private Long userId;

    @ApiModelProperty(value = "权益类型Id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.PRIZE_TYPE_ID)
    private Long prizeTypeId;

    @ApiModelProperty(value = "任务名称")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.NAME)
    private String taskName;

    @ApiModelProperty(value = "申领开始时间")
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.SUBMIT_TIME)
    private Date createTimeBegin;

    @ApiModelProperty(value = "申领结束时间")
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.SUBMIT_TIME)
    private Date createTimeEnd;

    @ApiModelProperty(value = "状态 1:待认领 2:已认领，待发放 3:已发放")
    @JoinColumn(tableClass = MemberTaskPrize.class, column = MemberTaskPrize.STATUS)
    private Integer status;

    @ApiModelProperty(value = "申领人")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.NICK)
    private String nick;

    @ApiModelProperty(value = "申领人Id")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.ACCOUNT_ID)
    private String accountId;

    @ApiModelProperty(value = "联系电话")
    @JoinColumn(tableClass = Member.class, column = Member.USER_PHONE)
    private String userPhone;

    private Boolean showWaitStatus = false;

    @Override
    public void where(JoinQueryBuilder<TaskPrizeJoinPageQuery> wrapper) {
        if (Objects.nonNull(this.getCreateTimeEnd())) {
            this.setCreateTimeEnd(DateUtil.endOfDay(this.getCreateTimeEnd()));
        }
        wrapper.orderByDesc(TaskPrizeJoinPageQuery::getCreateTimeBegin);
        if (!showWaitStatus) {
            wrapper.isNotNull(true, TaskPrizeJoinPageQuery::getCreateTimeBegin);
        }
        wrapper.eq(Objects.nonNull(taskId), TaskPrizeJoinPageQuery::getTaskId, getTaskId());
        wrapper.eq(Objects.nonNull(prizeTypeId), TaskPrizeJoinPageQuery::getPrizeTypeId, getPrizeTypeId());
        wrapper.eq(Objects.nonNull(userId), TaskPrizeJoinPageQuery::getUserId, getUserId());
        wrapper.eq(StrUtil.isNotBlank(accountId), TaskPrizeJoinPageQuery::getAccountId, getAccountId());
        wrapper.eq(Objects.nonNull(status), TaskPrizeJoinPageQuery::getStatus, getStatus());
        wrapper.eq(StrUtil.isNotBlank(createById), TaskPrizeJoinPageQuery::getCreateById, getCreateById());
        wrapper.eq(Objects.nonNull(userPrizeId), TaskPrizeJoinPageQuery::getUserPrizeId, getUserPrizeId());
        wrapper.like(StrUtil.isNotBlank(taskName), TaskPrizeJoinPageQuery::getTaskName, getTaskName());
        wrapper.eq(StrUtil.isNotBlank(nick), TaskPrizeJoinPageQuery::getNick, getNick());
        wrapper.eq(StrUtil.isNotBlank(userPhone), TaskPrizeJoinPageQuery::getUserPhone, getUserPhone());
        wrapper.gt(Objects.nonNull(this.getCreateTimeBegin()), TaskPrizeJoinPageQuery::getCreateTimeBegin, this.getCreateTimeBegin());
        wrapper.lt(Objects.nonNull(this.getCreateTimeEnd()), TaskPrizeJoinPageQuery::getCreateTimeEnd, this.getCreateTimeEnd());
    }
}
