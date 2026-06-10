package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.MallOrderNotifyWeChatCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.service.mall.OrderService;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderPaySuccessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class OrderNotifyWeChatCmdExe extends AbstractCmdExe<MallOrderNotifyWeChatCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private OrderService orderService;


    @Override
    protected SingleResponse handle(MallOrderNotifyWeChatCmd cmd) {
        String orderNo = cmd.getOutTradeNo();
        MallTrade trade = tradeService.lambdaQuery().eq(MallTrade::getOrderNo, orderNo).one();
        if (Objects.nonNull(trade)) {
            if (trade.getPayStatus().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getCode())) {
                return SingleResponse.of("ret=success");
            }
            OrderPaySuccessDTO dto = new OrderPaySuccessDTO();
            dto.setOrderNo(cmd.getOutTradeNo());
            dto.setAmount(cmd.getCashFee().toString());
            dto.setPaySuccessTime(new Date());
            dto.setBankSerialNo(cmd.getTransactionId());
            Boolean ret = orderService.paySuccess(dto);
            if (ret) {
                return SingleResponse.of("ret=success");
            }
        }
        return SingleResponse.of("fail");
    }
}
