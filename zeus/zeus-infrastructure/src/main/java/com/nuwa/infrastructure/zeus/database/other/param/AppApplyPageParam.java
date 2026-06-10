package com.nuwa.infrastructure.zeus.database.other.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.other.entity.AppApply;
import com.nuwa.client.zeus.dto.clientobject.other.qry.AppApplyPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * app试用申请 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-07-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "app试用申请分页参数")
public class AppApplyPageParam extends PageQry<AppApply> {
    private static final long serialVersionUID = 1L;

    private AppApplyPageQry qry;

    public AppApplyPageParam(AppApplyPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AppApply> toQueryWrapper() {
        LambdaQueryWrapper<AppApply> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(AppApply::getCreateTime);
        queryWrapper.select(AppApply.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        return queryWrapper;
    }
}
