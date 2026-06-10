package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUserCenter;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUserCenterPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通用户中心 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通用户中心分页参数")
public class OneUserCenterPageParam extends PageQry<OneUserCenter> {
    private static final long serialVersionUID = 1L;

    private OneUserCenterPageQry qry;

    public OneUserCenterPageParam(OneUserCenterPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneUserCenter> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<OneUserCenter> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(OneUserCenter::getCreateTime);
        queryWrapper.select(OneUserCenter.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getUserId()), OneUserCenter::getUserId, qry.getUserId());
        queryWrapper.eq(Objects.nonNull(qry.getUserIdCard()), OneUserCenter::getUserIdCard, qry.getUserIdCard());
        queryWrapper.eq(Objects.nonNull(qry.getUserPhone()), OneUserCenter::getUserPhone, qry.getUserPhone());
        queryWrapper.eq(Objects.nonNull(qry.getUserRealName()), OneUserCenter::getUserRealName, qry.getUserRealName());
        queryWrapper.like(!StrUtil.isBlankOrUndefined(qry.getUserNike()), OneUserCenter::getUserNike, qry.getUserNike());
        if (Objects.nonNull(qry.getCreateTimeEnd()) && Objects.nonNull(qry.getCreateTimeStart())) {
            queryWrapper.between(Objects.nonNull(qry.getCreateTimeStart()) && Objects.nonNull(qry.getCreateTimeEnd()), OneUserCenter::getCreateTime, DateUtil.beginOfDay(qry.getCreateTimeStart()), DateUtil.endOfDay(qry.getCreateTimeEnd()));
        }
        queryWrapper.eq(Objects.nonNull(userAware.getMchId()) && !userAware.getMchId().equals(-1L), OneUserCenter::getMchId, userAware.getMchId());
        return queryWrapper;
    }
}
