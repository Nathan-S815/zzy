package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseGroupPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class BaseGroupPageParam extends PageQry<BaseGroup> {
    private static final long serialVersionUID = 1L;

    private BaseGroupPageQry qry;

    public BaseGroupPageParam(BaseGroupPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<BaseGroup> toQueryWrapper() {
        LambdaQueryWrapper<BaseGroup> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(BaseGroup.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getCode()), BaseGroup::getCode, qry.getCode());
        queryWrapper.eq(!StrUtil.isBlankOrUndefined(qry.getName()), BaseGroup::getName, qry.getName());
        queryWrapper.eq(Objects.nonNull(qry.getParentId()), BaseGroup::getParentId, qry.getParentId());
        queryWrapper.eq(BaseGroup::getGroupType, 1);
        return queryWrapper;
    }
}
