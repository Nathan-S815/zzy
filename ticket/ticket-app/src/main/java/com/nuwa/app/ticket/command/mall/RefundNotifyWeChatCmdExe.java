package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.MallRefundNotifyWeChatCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.MallRefundNotifyWeChatCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.service.mall.OrderService;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderRefundSuccessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class RefundNotifyWeChatCmdExe extends AbstractCmdExe<MallRefundNotifyWeChatCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private OrderService orderService;


    @Override
    protected SingleResponse handle(MallRefundNotifyWeChatCmd cmd) {
        MallRefundNotifyWeChatCO co = cmd.getMallRefundNotifyWeChatCO();
        String tradeNo = co.getOutTradeNo();
        MallTrade trade = tradeService.lambdaQuery().eq(MallTrade::getTradeNo, tradeNo).one();
        if (Objects.nonNull(trade)) {
            if (trade.getPayStatus().equals(PaymentStatusEnum.REFUNDED.getCode())) {
                return SingleResponse.of("ret=success");
            }
            OrderRefundSuccessDTO dto = new OrderRefundSuccessDTO();
            dto.setOrderNo(co.getOutTradeNo());
            dto.setAmount(co.getRefundFee().toString());
            dto.setPlatOrderNo(co.getTransactionId());
            dto.setRefundTime(new Date());
            Boolean ret = orderService.refundSuccess(dto);
            if (ret) {
                return SingleResponse.of("ret=success");
            }
        }
        return SingleResponse.of("fail");
    }
}
