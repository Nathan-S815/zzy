package com.nuwa.app.ticket.statemachine;

import com.cmt.statemachine.StateMachine;
import com.cmt.statemachine.builder.StateMachineBuilder;
import com.cmt.statemachine.builder.StateMachineBuilderFactory;
import com.nuwa.app.ticket.statemachine.handle.ChannelPlaceOrderSucceededEventHandle;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEventEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TicketOrder StateMachine
 *
 * @author hy
 * @date 2021/4/20 19:54
 * @since 1.0.0
 */
@Configuration
public class TicketOrderStateMachineFactory {

    public static final String MACHINE_ID = "ticket_order_state_machine";

    @Autowired
    private ChannelPlaceOrderSucceededEventHandle ticketOrderEventHandle;

    @Bean
    public StateMachine<TicketOrderEnum, TicketOrderEventEnum> orderStateMachine() {
        StateMachineBuilder<TicketOrderEnum, TicketOrderEventEnum> builder = StateMachineBuilderFactory.create();
        builder.initialState(TicketOrderEnum.created);
        ticketOrderEventHandle.initEvent(builder);
       /* builder.noMatchStrategy(new NoMatchStrategy<States, Events>() {
            @Override
            public void process(States state, Events event) {
                throw new RuntimeException(state + " " + event);
            }
        });*/
        StateMachine<TicketOrderEnum, TicketOrderEventEnum> build = builder.build(MACHINE_ID);
        build.showStateMachine();
        return build;
    }
}
