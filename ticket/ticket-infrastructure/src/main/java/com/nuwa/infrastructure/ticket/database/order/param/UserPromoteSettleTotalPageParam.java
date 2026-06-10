package com.nuwa.infrastructure.ticket.database.order.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserPromoteSettlePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.order.entity.UserPromoteSettleRecord;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 用户待结算佣金分页参数
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户待结算佣金分页参数")
public class UserPromoteSettleTotalPageParam extends PageQry<UserPromoteSettleRecord> {
    private static final long serialVersionUID = 1L;

    private UserPromoteSettlePageQry qry;

    public UserPromoteSettleTotalPageParam(UserPromoteSettlePageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<UserPromoteSettleRecord> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<UserPromoteSettleRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(UserPromoteSettleRecord::getId);
        queryWrapper.eq(Objects.nonNull(qry.getUserId()), UserPromoteSettleRecord::getUserId, qry.getUserId());
        queryWrapper.eq(Objects.nonNull(qry.getScenicId()), UserPromoteSettleRecord::getScenicspotId, qry.getScenicId());
        return queryWrapper;
    }
}
