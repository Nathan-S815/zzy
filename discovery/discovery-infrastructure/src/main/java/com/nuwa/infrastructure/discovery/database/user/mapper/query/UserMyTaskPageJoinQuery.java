package com.nuwa.infrastructure.discovery.database.user.mapper.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.discovery.database.appconfig.entity.AppConfig;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "C端我的任务报名分页参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserMyTaskPageJoinQuery extends BaseJoinPagingQuery<UserMyTaskPageJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态 2:进行中 3:已结束")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.STATUS)
    private Integer status;

    @ApiModelProperty("达人id")
    @JoinColumn(tableClass = MemberTaskApply.class, column = MemberTaskApply.USER_ID)
    private Long userId;

    @ApiModelProperty("达人id")
    @JoinColumn(tableClass = MemberTaskApply.class, column = MemberTaskApply.CREATE_TIME)
    private Date createTime;

    @ApiModelProperty("商户Id")
    @JoinColumn(tableClass = ScenicTask.class, column = ScenicTask.MCH_ID)
    private String mchId;


    @Override
    public void where(JoinQueryBuilder<UserMyTaskPageJoinQuery> wrapper) {
        if (Objects.nonNull(status)) {
            if (status.equals(2)) {
                List<Integer> statusIn = new ArrayList<>();
                statusIn.add(2);
                statusIn.add(4);
                wrapper.in(UserMyTaskPageJoinQuery::getStatus, statusIn);
            } else {
                wrapper.eq(Objects.nonNull(getStatus()), UserMyTaskPageJoinQuery::getStatus, getStatus());
            }
        }
        wrapper.eq(Objects.nonNull(getMchId()),UserMyTaskPageJoinQuery::getMchId,getMchId());
        wrapper.eq(Objects.nonNull(getUserId()), UserMyTaskPageJoinQuery::getUserId, getUserId());
        wrapper.orderByDesc(UserMyTaskPageJoinQuery::getCreateTime);
    }
}
