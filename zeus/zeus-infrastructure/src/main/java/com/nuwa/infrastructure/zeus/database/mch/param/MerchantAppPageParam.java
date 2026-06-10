package com.nuwa.infrastructure.zeus.database.mch.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantAppPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户应用 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户应用分页参数")
public class MerchantAppPageParam extends PageQry<MerchantApp> {
    private static final long serialVersionUID = 1L;

    private MerchantAppPageQry qry;

    public MerchantAppPageParam(MerchantAppPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantApp> toQueryWrapper() {
        LambdaQueryWrapper<MerchantApp> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MerchantApp.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        return queryWrapper;
    }
}
