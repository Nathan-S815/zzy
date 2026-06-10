package com.nuwa.infrastructure.ticket.database.product.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantSupplierConfigPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户供应商配置 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户供应商配置分页参数")
public class MerchantSupplierConfigPageParam extends PageQry<MerchantSupplierConfig> {
    private static final long serialVersionUID = 1L;

    private MerchantSupplierConfigPageQry qry;

    public MerchantSupplierConfigPageParam(MerchantSupplierConfigPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantSupplierConfig> toQueryWrapper() {
        LambdaQueryWrapper<MerchantSupplierConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MerchantSupplierConfig.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
