package com.nuwa.infrastructure.attract.database.reward.param;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jodd.util.CollectionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.client.attract.dto.clientobject.reward.qry.RewardPageQry;

import java.util.Objects;

import com.nuwa.framework.database.PageQry;

/**
 * <pre>
 *  分页参数对象
 * </pre>
 *
 * @author nanhuang @南皇
 * @date 2022-09-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "分页参数")
public class RewardPageParam extends PageQry<Reward> {
    private static final long serialVersionUID = 1L;

    private RewardPageQry qry;

    public RewardPageParam(RewardPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<Reward> toQueryWrapper() {
        LambdaQueryWrapper<Reward> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(Reward.class,
            t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.orderByDesc(Reward::getRewardId);
        queryWrapper.eq(Objects.nonNull(qry.getReviewStatus()), Reward::getReviewStatus, qry.getReviewStatus());
        queryWrapper.in(CollectionUtils.isNotEmpty(qry.getUserIds()), Reward::getUserId, qry.getUserIds());
        return queryWrapper;
    }
}
