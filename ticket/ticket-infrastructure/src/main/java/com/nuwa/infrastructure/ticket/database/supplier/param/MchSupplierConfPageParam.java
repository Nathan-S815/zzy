package com.nuwa.infrastructure.ticket.database.supplier.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.order.qry.MchTicketOrderPageQry;
import com.nuwa.client.ticket.dto.clientobject.supplier.qry.SupplierConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <pre>
 * 商户供应商 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户供应商分页参数")
public class MchSupplierConfPageParam extends PageQry<MerchantSupplierConfig> {
    private static final long serialVersionUID = 1L;

    private SupplierConfPageQry qry;

    public MchSupplierConfPageParam(SupplierConfPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantSupplierConfig> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<MerchantSupplierConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(MerchantSupplierConfig::getId);
        queryWrapper.select(MerchantSupplierConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getName()), MerchantSupplierConfig::getName, qry.getName());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getChannelMerchantId()), MerchantSupplierConfig::getChannelMerchantId, qry.getChannelMerchantId());
        queryWrapper.eq(Objects.nonNull(qry.getSupplierId()), MerchantSupplierConfig::getSupplierId, qry.getSupplierId());
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), MerchantSupplierConfig::getMerchantId, userAware.getMchId());
        return queryWrapper;
    }
}
