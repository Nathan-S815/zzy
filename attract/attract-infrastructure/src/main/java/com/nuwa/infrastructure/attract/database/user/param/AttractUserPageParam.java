package com.nuwa.infrastructure.attract.database.user.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.client.attract.dto.clientobject.user.qry.AttractUserPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class AttractUserPageParam extends PageQry<AttractUser> {
    private static final long serialVersionUID = 1L;

    private AttractUserPageQry qry;

    public AttractUserPageParam(AttractUserPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<AttractUser> toQueryWrapper() {
        LambdaQueryWrapper<AttractUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(AttractUser.class,
            t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.like(StrUtil.isNotBlank(qry.getMchName()), AttractUser::getMchName, qry.getMchName());
        queryWrapper.eq(Objects.nonNull(qry.getReviewStatus()), AttractUser::getReviewStatus, qry.getReviewStatus());
        queryWrapper.eq(Objects.nonNull(qry.getAccountType()), AttractUser::getAccountType, qry.getAccountType());
        queryWrapper.orderByDesc(AttractUser::getCreateTime);
        return queryWrapper;
    }
}
