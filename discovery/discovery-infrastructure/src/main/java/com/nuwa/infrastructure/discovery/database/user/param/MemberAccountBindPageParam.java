package com.nuwa.infrastructure.discovery.database.user.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberAccountBindPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 达人账号绑定表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人账号绑定表分页参数")
public class MemberAccountBindPageParam extends PageQry<MemberAccountBind> {
    private static final long serialVersionUID = 1L;

    private MemberAccountBindPageQry qry;

    public MemberAccountBindPageParam(MemberAccountBindPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MemberAccountBind> toQueryWrapper() {
        LambdaQueryWrapper<MemberAccountBind> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MemberAccountBind.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getId()), MemberAccountBind::getId, qry.getId());
        return queryWrapper;
    }
}
