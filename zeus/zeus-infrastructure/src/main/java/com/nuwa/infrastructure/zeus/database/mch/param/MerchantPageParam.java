package com.nuwa.infrastructure.zeus.database.mch.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.infrastructure.zeus.enums.DeleteFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 商户信息 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户信息分页参数")
public class MerchantPageParam extends PageQry<Merchant> {
    private static final long serialVersionUID = 1L;

    private MerchantPageQry qry;

    public MerchantPageParam(MerchantPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Merchant> toQueryWrapper() {
        LambdaQueryWrapper<Merchant> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Merchant.class,
                t -> !t.getColumn().endsWith("_editor")
                        && !t.getColumn().equalsIgnoreCase(Merchant.UPDATE_TIME)
                        && !t.getColumn().equalsIgnoreCase(Merchant.DELETE_FLAG)
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getMchName()),Merchant::getMchName,qry.getMchName());
        queryWrapper.like(StrUtil.isNotBlank(qry.getAddress()),Merchant::getAddress,qry.getAddress());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getContentPhone()),Merchant::getContentPhone,qry.getContentPhone());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getUserName()),Merchant::getUserName,qry.getUserName());
        queryWrapper.eq(BeanUtil.isNotEmpty(qry.getStatus()),Merchant::getStatus,qry.getStatus());
        queryWrapper.eq(Merchant::getDeleteFlag, DeleteFlagEnum.NORMAL.getCode());
        queryWrapper.orderByDesc(Merchant::getCreateTime);
        return queryWrapper;
    }
}
