package com.nuwa.infrastructure.ticket.database.scenicspot.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.MerchantScenicspotPoi;
import com.nuwa.client.ticket.dto.clientobject.scenicspot.qry.MerchantScenicspotPoiPageQry;
import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户关联景区POI表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户关联景区POI表分页参数")
public class MerchantScenicspotPoiPageParam extends PageQry<MerchantScenicspotPoi> {
    private static final long serialVersionUID = 1L;

    private MerchantScenicspotPoiPageQry qry;

    public MerchantScenicspotPoiPageParam(MerchantScenicspotPoiPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantScenicspotPoi> toQueryWrapper() {
        LambdaQueryWrapper<MerchantScenicspotPoi> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(MerchantScenicspotPoi.class,
                t -> !t.getColumn().endsWith("_editor")
        );
            return queryWrapper;
    }
}
