package com.nuwa.infrastructure.discovery.database.task.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.discovery.dto.clientobject.task.qry.UserScenicTaskPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 景区任务表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户景区任务表分页参数")
public class UserScenicTaskPageParam extends PageQry<ScenicTask> {
    private static final long serialVersionUID = 1L;

    private UserScenicTaskPageQry qry;

    public UserScenicTaskPageParam(UserScenicTaskPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ScenicTask> toQueryWrapper() {
        LambdaQueryWrapper<ScenicTask> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScenicTask.class, t -> !t.getColumn().endsWith("_editor"));
        orderByProcess(queryWrapper);
        queryWrapper.like(Objects.nonNull(qry.getPrizeType()), ScenicTask::getPrizeTypeTag, "[" + qry.getPrizeType() + "]");
        queryWrapper.like(StrUtil.isNotBlank(qry.getTitle()), ScenicTask::getName, qry.getTitle());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getLat()), ScenicTask::getLat, qry.getLat());
        queryWrapper.le(Objects.nonNull(qry.getLimitLevel()), ScenicTask::getLimitLevel, qry.getLimitLevel());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getLon()), ScenicTask::getLon, qry.getLon());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), ScenicTask::getStatus, qry.getStatus());
        queryWrapper.eq(Objects.nonNull(qry.getIndexRecommend()), ScenicTask::getIndexRecommend, qry.getIndexRecommend());
        queryWrapper.eq(Objects.nonNull(qry.getMchId()), ScenicTask::getMchId, qry.getMchId());
        return queryWrapper;
    }

    private void orderByProcess(LambdaQueryWrapper<ScenicTask> queryWrapper) {
        //1:最新发布 2:最高分佣 3:最高热度
        Integer orderBy = qry.getOrderBy();
        if (Objects.nonNull(orderBy)) {
            if (orderBy.equals(1)) {
                queryWrapper.orderByDesc(ScenicTask::getId);
            } else if (orderBy.equals(2)) {
                queryWrapper.orderByDesc(ScenicTask::getChargeMax);
            } else if (orderBy.equals(3)) {
                queryWrapper.orderByDesc(ScenicTask::getIndexRecommend);
            }
        } else {
            queryWrapper.orderByDesc(ScenicTask::getIndexRecommend);
        }
        queryWrapper.orderByDesc(ScenicTask::getWeight);
    }
}
