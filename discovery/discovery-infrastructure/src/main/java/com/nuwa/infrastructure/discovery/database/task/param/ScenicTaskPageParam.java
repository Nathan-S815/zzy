package com.nuwa.infrastructure.discovery.database.task.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.client.discovery.dto.clientobject.task.qry.ScenicTaskPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

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
@ApiModel(value = "景区任务表分页参数")
public class ScenicTaskPageParam extends PageQry<ScenicTask> {
    private static final long serialVersionUID = 1L;

    private ScenicTaskPageQry qry;

    public ScenicTaskPageParam(ScenicTaskPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ScenicTask> toQueryWrapper() {
        LambdaQueryWrapper<ScenicTask> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScenicTask.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(qry.getEndDate())) {
            qry.setEndDate(DateUtil.endOfDay(qry.getEndDate()));
        }

        if (Objects.nonNull(qry.getCreateTimeEnd())) {
            qry.setCreateTimeEnd(DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.eq(Objects.nonNull(qry.getId()), ScenicTask::getId, qry.getId());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getLinkman()), ScenicTask::getLinkman, qry.getLinkman());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getLinkmanTelephone()), ScenicTask::getLinkmanTelephone, qry.getLinkmanTelephone());
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd()), ScenicTask::getCreateTime, qry.getCreateTimeStart(), qry.getCreateTimeEnd());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getPlatformCode()), ScenicTask::getPlatformCode, qry.getPlatformCode());
        queryWrapper.ge(Objects.nonNull(qry.getBeginDate()), ScenicTask::getBeginDate, qry.getBeginDate());
        queryWrapper.le(Objects.nonNull(qry.getEndDate()), ScenicTask::getEndDate, qry.getEndDate());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), ScenicTask::getStatus, qry.getStatus());
        queryWrapper.eq(Objects.nonNull(qry.getSource()), ScenicTask::getSource, qry.getSource());
        queryWrapper.eq(Objects.nonNull(qry.getCreateById()), ScenicTask::getCreateById, qry.getCreateById());
        queryWrapper.like(Objects.nonNull(qry.getPrizeType()), ScenicTask::getPrizeTypeTag, "[" + qry.getPrizeType() + "]");
        queryWrapper.like(StrUtil.isNotBlank(qry.getName()),ScenicTask::getName, qry.getName());
        queryWrapper.orderByAsc(ScenicTask::getWeight);
        queryWrapper.orderByDesc(ScenicTask::getId);
        return queryWrapper;
    }
}
