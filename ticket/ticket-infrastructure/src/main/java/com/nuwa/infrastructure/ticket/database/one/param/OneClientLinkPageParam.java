package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneClientLink;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneClientLinkPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通功能链接配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通功能链接配置分页参数")
public class OneClientLinkPageParam extends PageQry<OneClientLink> {
    private static final long serialVersionUID = 1L;

    private OneClientLinkPageQry qry;

    public OneClientLinkPageParam(OneClientLinkPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneClientLink> toQueryWrapper() {
        LambdaQueryWrapper<OneClientLink> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(OneClientLink::getSortNum);
        queryWrapper.select(OneClientLink.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getMchId()), OneClientLink::getMchId, qry.getMchId());
        return queryWrapper;
    }
}
