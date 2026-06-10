package com.nuwa.infrastructure.ticket.database.activity.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityCategoryPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.activity.entity.CultureActivityCategory;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 活动类别 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-08-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "活动类别分页参数")
public class CultureActivityCategoryPageParam extends PageQry<CultureActivityCategory> {
    private static final long serialVersionUID = 1L;

    private CultureActivityCategoryPageQry qry;

    public CultureActivityCategoryPageParam(CultureActivityCategoryPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CultureActivityCategory> toQueryWrapper() {
        LambdaQueryWrapper<CultureActivityCategory> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CultureActivityCategory.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(CultureActivityCategory::getMchId,qry.getUserAware().getMchId());
        queryWrapper.eq(CultureActivityCategory::getAppId,qry.getAppId());
        queryWrapper.eq(CultureActivityCategory::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.like(StrUtil.isNotBlank(qry.getCategoryName()),CultureActivityCategory::getCategoryName,qry.getCategoryName());
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getPublishStatus()),CultureActivityCategory::getPublishStatus,qry.getPublishStatus());
        return queryWrapper;
    }
}
