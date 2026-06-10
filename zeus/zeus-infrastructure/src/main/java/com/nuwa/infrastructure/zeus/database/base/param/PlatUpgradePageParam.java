package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.base.entity.PlatUpgrade;
import com.nuwa.client.zeus.dto.clientobject.base.qry.PlatUpgradePageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 平台升级日志 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "平台升级日志分页参数")
public class PlatUpgradePageParam extends PageQry<PlatUpgrade> {
    private static final long serialVersionUID = 1L;

    private PlatUpgradePageQry qry;

    public PlatUpgradePageParam(PlatUpgradePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<PlatUpgrade> toQueryWrapper() {
        LambdaQueryWrapper<PlatUpgrade> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(PlatUpgrade.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(StrUtil.isNotBlank(qry.getTitle()),PlatUpgrade::getTitle,qry.getTitle());
        queryWrapper.eq(PlatUpgrade::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.orderByDesc(PlatUpgrade::getCreateTime);
        return queryWrapper;
    }
}
