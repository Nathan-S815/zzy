package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneClientConfigPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.one.entity.OneClientConfig;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 一码通端口配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通端口配置分页参数")
public class OneClientConfigPageParam extends PageQry<OneClientConfig> {
    private static final long serialVersionUID = 1L;

    private OneClientConfigPageQry qry;

    public OneClientConfigPageParam(OneClientConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneClientConfig> toQueryWrapper() {
        LambdaQueryWrapper<OneClientConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(OneClientConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getName()), OneClientConfig::getName, qry.getName());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getAppType()), OneClientConfig::getAppType, qry.getAppType());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getOutAppId()), OneClientConfig::getOutAppId, qry.getOutAppId());
        queryWrapper.eq(Objects.nonNull(qry.getUserAware().getMchId()), OneClientConfig::getMchId, qry.getUserAware().getMchId());
        return queryWrapper;
    }
}
