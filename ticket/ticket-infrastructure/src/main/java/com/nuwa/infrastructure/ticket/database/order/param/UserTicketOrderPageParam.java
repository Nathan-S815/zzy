package com.nuwa.infrastructure.ticket.database.order.param;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserTicketOrderPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * <pre>
 * 订单表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单表分页参数")
public class UserTicketOrderPageParam extends PageQry<TicketOrder> {
    private static final long serialVersionUID = 1L;

    private UserTicketOrderPageQry qry;

    public UserTicketOrderPageParam(UserTicketOrderPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TicketOrder> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<TicketOrder> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(TicketOrder::getId);
        queryWrapper.select(TicketOrder.class,
                t -> !t.getColumn().endsWith("_editor")
        );
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), TicketOrder::getStatus, qry.getStatus());
        queryWrapper.ne(Objects.isNull(qry.getStatus()), TicketOrder::getStatus, TicketOrderEnum.created.getCode());
        queryWrapper.eq(Objects.nonNull(userAware.getUserId()), TicketOrder::getUserId, userAware.getUserId());
        return queryWrapper;
    }
}
