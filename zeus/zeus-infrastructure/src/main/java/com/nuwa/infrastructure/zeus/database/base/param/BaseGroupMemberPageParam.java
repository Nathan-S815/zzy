package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseGroupMemberPageQry;
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
public class BaseGroupMemberPageParam extends PageQry<BaseGroupMember> {
    private static final long serialVersionUID = 1L;

    private BaseGroupMemberPageQry qry;

    public BaseGroupMemberPageParam(BaseGroupMemberPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<BaseGroupMember> toQueryWrapper() {
        LambdaQueryWrapper<BaseGroupMember> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(BaseGroupMember.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
