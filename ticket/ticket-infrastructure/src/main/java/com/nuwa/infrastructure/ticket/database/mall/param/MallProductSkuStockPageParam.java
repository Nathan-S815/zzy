package com.nuwa.infrastructure.ticket.database.mall.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductSkuStockPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 商品规格表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商品规格表分页参数")
public class MallProductSkuStockPageParam extends PageQry<MallProductSkuStock> {
    private static final long serialVersionUID = 1L;

    private MallProductSkuStockPageQry qry;

    public MallProductSkuStockPageParam(MallProductSkuStockPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MallProductSkuStock> toQueryWrapper() {
        LambdaQueryWrapper<MallProductSkuStock> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MallProductSkuStock.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.CREATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.UPDATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.DELETE_FLAG)
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.MCH_ID)
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.CREATE_BY)
                        && !t.getColumn().equalsIgnoreCase(MallProductSkuStock.UPDATE_BY)
        );
        queryWrapper.eq(Objects.nonNull(qry.getId()), MallProductSkuStock::getId, qry.getId());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getSkuStockName()), MallProductSkuStock::getSkuStockName, qry.getSkuStockName());
        return queryWrapper;
    }
}
