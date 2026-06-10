package com.nuwa.infrastructure.discovery.database.user.mapper.query;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
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
@ApiModel(value = "用户任务报名分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserTaskJoinPageQuery extends BaseJoinPagingQuery<UserTaskJoinPageQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务id")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.ID)
    private Long taskId;

    @ApiModelProperty("达人用户id")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.ACCOUNT_ID)
    private String accountId;

    @ApiModelProperty("达人id")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.USER_ID)
    private String userId;

    @ApiModelProperty("达人昵称")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.NICK)
    private String nick;

    @ApiModelProperty("达人昵称")
    @JoinColumn(tableClass = Member.class, column = Member.USER_NIKE)
    private String userNike;

    @ApiModelProperty("开始报名时间")
    @JoinColumn(tableClass = MemberTaskApply.class, column = MemberTaskApply.CREATE_TIME)
    private Date createTimeBegin;

    @ApiModelProperty("结束报名时间")
    @JoinColumn(tableClass = MemberTaskApply.class, column = MemberTaskApply.CREATE_TIME)
    private Date createTimeEnd;

    @ApiModelProperty("达人等级")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.LEVEL)
    private String level;

    @ApiModelProperty("达人性别 1:男 0:女")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.SEX)
    private Integer sex;

    @ApiModelProperty("达人地域")
    @JoinColumn(tableClass = MemberAccountBind.class, column = MemberAccountBind.REGION)
    private String region;

    @Override
    public void where(JoinQueryBuilder<UserTaskJoinPageQuery> wrapper) {
        wrapper.orderByDesc(UserTaskJoinPageQuery::getCreateTimeBegin);
        wrapper.eq(StrUtil.isNotEmpty(this.getUserNike()), UserTaskJoinPageQuery::getUserNike, this.getUserNike());
        wrapper.eq(StrUtil.isNotEmpty(this.getUserId()), UserTaskJoinPageQuery::getUserId, this.getUserId());
        wrapper.eq(StrUtil.isNotEmpty(this.getAccountId()), UserTaskJoinPageQuery::getAccountId, this.getAccountId());
        wrapper.eq(StrUtil.isNotEmpty(this.getNick()), UserTaskJoinPageQuery::getNick, this.getNick());
        wrapper.eq(StrUtil.isNotEmpty(this.getLevel()), UserTaskJoinPageQuery::getLevel, this.getLevel());
        wrapper.eq(Objects.nonNull(this.getSex()), UserTaskJoinPageQuery::getSex, this.getSex());
        wrapper.eq(Objects.nonNull(this.getTaskId()), UserTaskJoinPageQuery::getTaskId, this.getTaskId());
        wrapper.like(StrUtil.isNotEmpty(this.getRegion()), UserTaskJoinPageQuery::getRegion, this.getRegion());
        wrapper.gt(Objects.nonNull(this.getCreateTimeBegin()), UserTaskJoinPageQuery::getCreateTimeBegin, this.getCreateTimeBegin());
        wrapper.lt(Objects.nonNull(this.getCreateTimeEnd()), UserTaskJoinPageQuery::getCreateTimeEnd, this.getCreateTimeEnd());
    }
}
