package com.nuwa.infrastructure.ticket.database.product.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import com.nuwa.client.ticket.dto.clientobject.product.qry.ScenicspotProductPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class ScenicspotProductPageParam extends PageQry<ScenicspotProduct> {
    private static final long serialVersionUID = 1L;

    private ScenicspotProductPageQry qry;

    public ScenicspotProductPageParam(ScenicspotProductPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<ScenicspotProduct> toQueryWrapper() {
        LambdaQueryWrapper<ScenicspotProduct> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(ScenicspotProduct.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
