package com.nuwa.infrastructure.discovery.database.user.param;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskApply;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberTaskApplyPageQry;

import java.util.Date;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 达人任务报名表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人任务报名表分页参数")
public class MemberTaskApplyPageParam extends PageQry<MemberTaskApply> {
    private static final long serialVersionUID = 1L;

    private MemberTaskApplyPageQry qry;

    public MemberTaskApplyPageParam(MemberTaskApplyPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MemberTaskApply> toQueryWrapper() {
        LambdaQueryWrapper<MemberTaskApply> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MemberTaskApply.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(qry.getCreateTimeEnd())) {
            qry.setCreateTimeEnd(DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd()), MemberTaskApply::getCreateTime, qry.getCreateTimeStart(), qry.getCreateTimeEnd());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), MemberTaskApply::getStatus, qry.getStatus());
        return queryWrapper;
    }
}
