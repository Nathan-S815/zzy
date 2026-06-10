package com.nuwa.ticket.start.api.controller.scenicspot;

import com.alibaba.cola.dto.SingleResponse;
import com.cmt.statemachine.StateMachine;
import com.nuwa.app.ticket.statemachine.context.ChannelPlaceOrderStateContext;
import com.nuwa.app.ticket.statemachine.result.TicketOrderEventHandleResult;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEventEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("test")
@Api(tags = {"test"})
public class StateMachineTestController {

    @Autowired
    private StateMachine<TicketOrderEnum, TicketOrderEventEnum> ticketOrderStateMachine;

    @ApiOperation(value = "test")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> test() {
        ChannelPlaceOrderStateContext orderStateContext = new ChannelPlaceOrderStateContext();
        orderStateContext.setOrderId(1988L);
        SingleResponse<TicketOrderEventHandleResult> response =
                ticketOrderStateMachine.fireEventWithResult(TicketOrderEnum.created, TicketOrderEventEnum.supplierPlaceOrderFailed, orderStateContext);
        if (Objects.isNull(response)) {
            log.warn("no match EventHandle");
        }
        return response;
    }
}
