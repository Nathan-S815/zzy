package com.nuwa.infrastructure.ticket.database.activity.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivity;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 文化活动 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动分页参数")
public class CultureActivityPageParam extends PageQry<CultureActivity> {
    private static final long serialVersionUID = 1L;

    private CultureActivityPageQry qry;

    public CultureActivityPageParam(CultureActivityPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CultureActivity> toQueryWrapper() {
        LambdaQueryWrapper<CultureActivity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CultureActivity.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(CultureActivity::getMchId, qry.getUserAware().getMchId());
        queryWrapper.eq(CultureActivity::getAppId, qry.getAppId());
        queryWrapper.eq(Objects.nonNull(qry.getCategoryId()), CultureActivity::getCategoryId, qry.getCategoryId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getActivityTitle()), CultureActivity::getActivityTitle, qry.getActivityTitle());
        queryWrapper.ge(Objects.nonNull(qry.getHoldTimeStart()), CultureActivity::getHoldTimeStart, qry.getHoldTimeStart());
        queryWrapper.le(Objects.nonNull(qry.getHoldTimeEnd()), CultureActivity::getHoldTimeEnd, qry.getHoldTimeEnd());
        queryWrapper.eq(CultureActivity::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        return queryWrapper;
    }
}
