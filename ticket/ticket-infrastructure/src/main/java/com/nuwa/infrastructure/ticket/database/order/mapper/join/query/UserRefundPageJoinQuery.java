package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "用户退款订单分页Query")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserRefundPageJoinQuery extends BaseJoinPagingQuery<UserRefundPageJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.ID)
    private Long id;

    @ApiModelProperty("订单id")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.ORDER_ID)
    private Long orderId;

    @ApiModelProperty("用户id")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.USER_ID)
    private Long userId;

    @ApiModelProperty("状态")
    @JoinColumn(tableClass = TicketRefund.class, column = TicketRefund.STATUS)
    private Integer status;

    @Override
    public void where(JoinQueryBuilder<UserRefundPageJoinQuery> wrapper) {
        wrapper.orderByDesc(UserRefundPageJoinQuery::getId);
        wrapper.eq(Objects.nonNull(userId), UserRefundPageJoinQuery::getUserId, userId);
        wrapper.eq(Objects.nonNull(orderId), UserRefundPageJoinQuery::getOrderId, orderId);
        if (Objects.nonNull(status)) {
            //  创建:1  退款中:2  已退款:3  退款失败:4
            if (status.equals(1)) {
                List<Integer> statusIn = new ArrayList<>();
                statusIn.add(1);
                statusIn.add(2);
                wrapper.in(UserRefundPageJoinQuery::getStatus, statusIn);
            } else if (status.equals(2)) {
                wrapper.eq(UserRefundPageJoinQuery::getStatus, 3);
            }else if (status.equals(3)) {
                wrapper.eq(UserRefundPageJoinQuery::getStatus, 4);
            }
        }
    }
}
