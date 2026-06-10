package com.nuwa.app.ticket.statemachine.context;

import com.cmt.statemachine.impl.DefaultStateAwareImpl;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelPlaceOrderStateContext extends DefaultStateAwareImpl<TicketOrderEnum> {
    private Long orderId;
}
