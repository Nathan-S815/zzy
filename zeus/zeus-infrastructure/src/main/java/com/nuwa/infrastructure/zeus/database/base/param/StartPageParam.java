package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.zeus.dto.clientobject.base.qry.GetStartPageQry;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.zeus.database.base.entity.GettingStarted;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 帮助中心分页参数
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "帮助中心分页参数")
public class StartPageParam extends PageQry<GettingStarted> {
    private static final long serialVersionUID = 1L;

    private GetStartPageQry qry;

    public StartPageParam(GetStartPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<GettingStarted> toQueryWrapper() {
        LambdaQueryWrapper<GettingStarted> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(GettingStarted.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(StrUtil.isNotBlank(qry.getTitle()), GettingStarted::getTitle, qry.getTitle());
        queryWrapper.eq(GettingStarted::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.eq(GettingStarted::getType, "content");
        queryWrapper.orderByDesc(GettingStarted::getCreateTime);
        return queryWrapper;
    }
}
