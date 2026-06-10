package com.nuwa.infrastructure.ticket.database.appconf.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.appconf.qry.MerchantAppConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
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
@ApiModel(value = "商户供应商分页参数")
public class MchAppConfPageParam extends PageQry<MerchantAppBaseConf> {
    private static final long serialVersionUID = 1L;

    private MerchantAppConfPageQry qry;

    public MchAppConfPageParam(MerchantAppConfPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<MerchantAppBaseConf> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<MerchantAppBaseConf> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(MerchantAppBaseConf::getId);
        queryWrapper.select(MerchantAppBaseConf.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getAppName()), MerchantAppBaseConf::getAppName, qry.getAppName());
        queryWrapper.eq(Objects.nonNull(qry.getAppType()), MerchantAppBaseConf::getAppType, qry.getAppType());
        queryWrapper.eq(Objects.nonNull(qry.getProvinceId()), MerchantAppBaseConf::getProvinceId, qry.getProvinceId());
        queryWrapper.eq(Objects.nonNull(qry.getCityId()), MerchantAppBaseConf::getCityId, qry.getCityId());
        queryWrapper.eq(Objects.nonNull(qry.getOutAppId()), MerchantAppBaseConf::getOutAppId, qry.getOutAppId());
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), MerchantAppBaseConf::getMchId, userAware.getMchId());
        return queryWrapper;
    }
}
