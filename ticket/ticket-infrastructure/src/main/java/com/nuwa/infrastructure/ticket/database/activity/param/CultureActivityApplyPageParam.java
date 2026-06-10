package com.nuwa.infrastructure.ticket.database.activity.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityApplyPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityApply;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 文化活动报名 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动报名分页参数")
public class CultureActivityApplyPageParam extends PageQry<CultureActivityApply> {
    private static final long serialVersionUID = 1L;

    private CultureActivityApplyPageQry qry;

    public CultureActivityApplyPageParam(CultureActivityApplyPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CultureActivityApply> toQueryWrapper() {
        LambdaQueryWrapper<CultureActivityApply> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CultureActivityApply.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(CultureActivityApply::getMchId, qry.getUserAware().getMchId());
        queryWrapper.eq(CultureActivityApply::getAppId, qry.getAppId());
        queryWrapper.and(StrUtil.isNotBlank(qry.getKeyword()),x->x.eq(!StrUtil.isBlankOrUndefined(qry.getKeyword()), CultureActivityApply::getContactsName,qry.getKeyword())
                .or().eq(!StrUtil.isBlankOrUndefined(qry.getKeyword()),CultureActivityApply::getContactsMobile,qry.getKeyword()));
        return queryWrapper;
    }
}
