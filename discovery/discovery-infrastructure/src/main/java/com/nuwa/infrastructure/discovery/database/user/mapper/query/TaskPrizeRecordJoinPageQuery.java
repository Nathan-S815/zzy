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
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrizeRecord;
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
@ApiModel(value = "用户任务权益申领记录分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskPrizeRecordJoinPageQuery extends BaseJoinPagingQuery<TaskPrizeRecordJoinPageQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.ID)
    private Long taskId;

    @ApiModelProperty(value = "用户权益记录id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.ID)
    private Long prizeRecordId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.USER_ID)
    private Long userId;

    @ApiModelProperty(value = "权益类型Id", hidden = true)
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.PRIZE_TYPE_ID)
    private Long prizeTypeId;

    @ApiModelProperty(value = "任务名称")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.NAME)
    private String taskName;

    @ApiModelProperty(value = "申领开始时间")
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.SUBMIT_TIME)
    private Date createTimeBegin;

    @ApiModelProperty(value = "申领结束时间")
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.SUBMIT_TIME)
    private Date createTimeEnd;

    @ApiModelProperty(value = "状态 1:待认领 2:已认领，待发放 3:已发放")
    @JoinColumn(tableClass = MemberTaskPrizeRecord.class, column = MemberTaskPrizeRecord.STATUS)
    private Integer status;

    @ApiModelProperty(value = "申领人")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.NICK)
    private String nick;

    @ApiModelProperty(value = "联系电话")
    @JoinColumn(tableClass = Member.class, column = Member.USER_PHONE)
    private String userPhone;

    @Override
    public void where(JoinQueryBuilder<TaskPrizeRecordJoinPageQuery> wrapper) {
        if (Objects.nonNull(this.getCreateTimeEnd())) {
            this.setCreateTimeEnd(DateUtil.endOfDay(this.getCreateTimeEnd()));
        }
        wrapper.orderByDesc(TaskPrizeRecordJoinPageQuery::getCreateTimeBegin);
        wrapper.eq(Objects.nonNull(taskId), TaskPrizeRecordJoinPageQuery::getTaskId, getTaskId());
        wrapper.eq(Objects.nonNull(prizeTypeId), TaskPrizeRecordJoinPageQuery::getPrizeTypeId, getPrizeTypeId());
        wrapper.eq(Objects.nonNull(userId), TaskPrizeRecordJoinPageQuery::getUserId, getUserId());
        wrapper.eq(Objects.nonNull(status), TaskPrizeRecordJoinPageQuery::getStatus, getStatus());
        wrapper.eq(Objects.nonNull(prizeRecordId), TaskPrizeRecordJoinPageQuery::getPrizeRecordId, getPrizeRecordId());
        wrapper.like(StrUtil.isNotBlank(taskName), TaskPrizeRecordJoinPageQuery::getTaskName, getTaskName());
        wrapper.eq(StrUtil.isNotBlank(nick), TaskPrizeRecordJoinPageQuery::getNick, getNick());
        wrapper.eq(StrUtil.isNotBlank(userPhone), TaskPrizeRecordJoinPageQuery::getUserPhone, getUserPhone());
        wrapper.gt(Objects.nonNull(this.getCreateTimeBegin()), TaskPrizeRecordJoinPageQuery::getCreateTimeBegin, this.getCreateTimeBegin());
        wrapper.lt(Objects.nonNull(this.getCreateTimeEnd()), TaskPrizeRecordJoinPageQuery::getCreateTimeEnd, this.getCreateTimeEnd());
    }
}
