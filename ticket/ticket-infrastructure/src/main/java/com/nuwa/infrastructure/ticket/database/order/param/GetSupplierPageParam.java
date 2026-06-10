package com.nuwa.infrastructure.ticket.database.order.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantGetSupplierPageQry;
import com.nuwa.client.ticket.dto.clientobject.user.qry.UserPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSaasSupplier;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

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
@ApiModel(value = "用户分页参数")
public class GetSupplierPageParam extends PageQry<MerchantSaasSupplier> {
    private static final long serialVersionUID = 1L;

    private MerchantGetSupplierPageQry qry;

    public GetSupplierPageParam(MerchantGetSupplierPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantSaasSupplier> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<MerchantSaasSupplier> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(MerchantSaasSupplier::getId);
        queryWrapper.select(MerchantSaasSupplier.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getSupplierName()), MerchantSaasSupplier::getSupplierName, qry.getSupplierName());
        queryWrapper.eq(Objects.nonNull(qry.getDistributeMerchantId()), MerchantSaasSupplier::getDistributeMerchantId, qry.getDistributeMerchantId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getDistributeName()), MerchantSaasSupplier::getDistributeName, qry.getDistributeName());
        queryWrapper.eq(Objects.nonNull(qry.getSupplierMerchantId()), MerchantSaasSupplier::getSupplierMerchantId, qry.getSupplierMerchantId());
        return queryWrapper;
    }
}
