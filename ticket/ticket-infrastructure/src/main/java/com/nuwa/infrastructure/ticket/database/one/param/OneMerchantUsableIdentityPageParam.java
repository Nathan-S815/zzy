package com.nuwa.infrastructure.ticket.database.one.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantUsableIdentity;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneMerchantUsableIdentityPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 * 一码通商户端可用身份认 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通商户端可用身份认分页参数")
public class OneMerchantUsableIdentityPageParam extends PageQry<OneMerchantUsableIdentity> {
    private static final long serialVersionUID = 1L;

    private OneMerchantUsableIdentityPageQry qry;

    public OneMerchantUsableIdentityPageParam(OneMerchantUsableIdentityPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<OneMerchantUsableIdentity> toQueryWrapper() {
        LambdaQueryWrapper<OneMerchantUsableIdentity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Objects.nonNull(qry.getMchId()), OneMerchantUsableIdentity::getMchId, qry.getMchId());
        queryWrapper.select(OneMerchantUsableIdentity.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        return queryWrapper;
    }
}
