package com.nuwa.infrastructure.zeus.database.base.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.zeus.dto.clientobject.base.qry.BaseUserPageQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildPageQry;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class MerchantChildPageParam extends PageQry<BaseUser> {
    private static final long serialVersionUID = 1L;

    private MerchantChildPageQry qry;

    public MerchantChildPageParam(MerchantChildPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<BaseUser> toQueryWrapper() {
        LambdaQueryWrapper<BaseUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(BaseUser.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(BaseUser::getTenantId,qry.getUserAware().getMchId());
        queryWrapper.eq(BaseUser::getType, AdminCommonConstant.NORMAL_USER_TYPE);
        queryWrapper.like(StrUtil.isNotBlank(qry.getUserName()),BaseUser::getUsername, qry.getUserName());
        return queryWrapper;
    }
}
