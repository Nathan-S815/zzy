package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.ServiceOpenApiConfig;
import com.nuwa.client.ticket.dto.clientobject.one.qry.ServiceOpenApiConfigPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通服务商开放API 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通服务商开放API分页参数")
public class ServiceOpenApiConfigPageParam extends PageQry<ServiceOpenApiConfig> {
    private static final long serialVersionUID = 1L;

    private ServiceOpenApiConfigPageQry qry;

    public ServiceOpenApiConfigPageParam(ServiceOpenApiConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ServiceOpenApiConfig> toQueryWrapper() {
        LambdaQueryWrapper<ServiceOpenApiConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ServiceOpenApiConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
