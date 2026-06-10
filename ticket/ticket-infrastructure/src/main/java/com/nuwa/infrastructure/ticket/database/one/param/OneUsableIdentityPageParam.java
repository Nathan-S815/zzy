package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUsableIdentityPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通可用身份认证配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通可用身份认证配置分页参数")
public class OneUsableIdentityPageParam extends PageQry<OneUsableIdentity> {
    private static final long serialVersionUID = 1L;

    private OneUsableIdentityPageQry qry;

    public OneUsableIdentityPageParam(OneUsableIdentityPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneUsableIdentity> toQueryWrapper() {
        LambdaQueryWrapper<OneUsableIdentity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(OneUsableIdentity.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getName()), OneUsableIdentity::getName, qry.getName());
        queryWrapper.eq(OneUsableIdentity::getStatus, "on");
        return queryWrapper;
    }
}
