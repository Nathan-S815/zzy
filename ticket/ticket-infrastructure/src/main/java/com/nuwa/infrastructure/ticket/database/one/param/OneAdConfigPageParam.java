package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneAdConfig;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneAdConfigPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通广告配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通广告配置分页参数")
public class OneAdConfigPageParam extends PageQry<OneAdConfig> {
    private static final long serialVersionUID = 1L;

    private OneAdConfigPageQry qry;

    public OneAdConfigPageParam(OneAdConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneAdConfig> toQueryWrapper() {
        LambdaQueryWrapper<OneAdConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(OneAdConfig::getOrderNum);
        queryWrapper.select(OneAdConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getOneClientId()), OneAdConfig::getOneClientId, qry.getOneClientId());
        return queryWrapper;
    }
}
