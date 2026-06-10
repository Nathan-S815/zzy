package com.nuwa.infrastructure.discovery.database.user.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberTaskPrizePageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 达人任务权益表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人任务权益表分页参数")
public class MemberTaskPrizePageParam extends PageQry<MemberTaskPrize> {
    private static final long serialVersionUID = 1L;

    private MemberTaskPrizePageQry qry;

    public MemberTaskPrizePageParam(MemberTaskPrizePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MemberTaskPrize> toQueryWrapper() {
        LambdaQueryWrapper<MemberTaskPrize> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(qry.getCreateTimeEnd())) {
            qry.setCreateTimeEnd(DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.select(MemberTaskPrize.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getName()), MemberTaskPrize::getName, qry.getName());
        queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd()), MemberTaskPrize::getCreateTime, qry.getCreateTimeStart(), qry.getCreateTimeEnd());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getPlatformCode()), MemberTaskPrize::getPlatformCode, qry.getPlatformCode());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), MemberTaskPrize::getStatus, qry.getStatus());
        return queryWrapper;
    }
}
