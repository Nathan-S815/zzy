package com.nuwa.app.ticket.statemachine.handle;

import com.alibaba.cola.dto.SingleResponse;
import com.cmt.statemachine.Action;
import com.cmt.statemachine.Condition;
import com.cmt.statemachine.builder.StateMachineBuilder;
import com.nuwa.app.ticket.statemachine.EventProcessor;
import com.nuwa.app.ticket.statemachine.context.ChannelPlaceOrderStateContext;
import com.nuwa.app.ticket.statemachine.result.TicketOrderEventHandleResult;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEventEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * channelPlaceOrderSucceeded 事件处理器
 *
 * @author hy
 */
@Slf4j
@Component
public class ChannelPlaceOrderSucceededEventHandle implements
        Action<ChannelPlaceOrderStateContext, SingleResponse<TicketOrderEventHandleResult>>,
        Condition<ChannelPlaceOrderStateContext>,
        EventProcessor<TicketOrderEnum, TicketOrderEventEnum> {

    @Override
    public void initEvent(StateMachineBuilder<TicketOrderEnum, TicketOrderEventEnum> builder) {
        builder.externalTransition()
                .from(TicketOrderEnum.created)
                .to(TicketOrderEnum.paying)
                .on(TicketOrderEventEnum.channelPlaceOrderSucceeded)
                .when(this)
                .perform(this);
        builder.externalTransition()
                .from(TicketOrderEnum.paying)
                .to(TicketOrderEnum.ticketing)
                .on(TicketOrderEventEnum.supplierPaymentSucceeded)
                .when(this)
                .perform(this);
        log.info("initEvent");
    }

    @Override
    public SingleResponse<TicketOrderEventHandleResult> execute(ChannelPlaceOrderStateContext ticketOrderStateContext) {
        log.info("execute ticketOrderStateContext:{}", ticketOrderStateContext);
        TicketOrderEventHandleResult result = new TicketOrderEventHandleResult();
        result.setId("112233");
        return SingleResponse.of(result);
    }

    @Override
    public boolean isSatisfied(ChannelPlaceOrderStateContext ticketOrderStateContext) {
        log.info("checkCondition ticketOrderStateContext:{}", ticketOrderStateContext);
        return true;
    }
}
