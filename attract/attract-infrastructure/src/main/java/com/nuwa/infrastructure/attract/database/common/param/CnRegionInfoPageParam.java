package com.nuwa.infrastructure.attract.database.common.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.common.entity.CnRegionInfo;
import com.nuwa.client.attract.dto.clientobject.common.qry.CnRegionInfoPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 中国地区信息 分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "中国地区信息分页参数")
public class CnRegionInfoPageParam extends PageQry<CnRegionInfo> {
    private static final long serialVersionUID = 1L;

    private CnRegionInfoPageQry qry;

    public CnRegionInfoPageParam(CnRegionInfoPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<CnRegionInfo> toQueryWrapper() {
        LambdaQueryWrapper<CnRegionInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(CnRegionInfo.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
