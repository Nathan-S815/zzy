package com.nuwa.infrastructure.ticket.database.payconfig.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.payconfig.qry.PayConfigPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.gateway.entity.MerchantPayGatewayConfig;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 支付平台配置分页参数
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "支付平台配置分页参数")
public class PayConfigPageParam extends PageQry<MerchantPayGatewayConfig> {
    private static final long serialVersionUID = 1L;

    private PayConfigPageQry qry;

    public PayConfigPageParam(PayConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantPayGatewayConfig> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<MerchantPayGatewayConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(MerchantPayGatewayConfig::getId);
        queryWrapper.select(MerchantPayGatewayConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        if (Objects.nonNull(qry.getMchId())) {
            queryWrapper.eq(MerchantPayGatewayConfig::getMchId, userAware.getMchId());
        } else {
            queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), MerchantPayGatewayConfig::getMchId, userAware.getMchId());
        }
        return queryWrapper;
    }
}
