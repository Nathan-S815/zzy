package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.RefundTradeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefundMallTradeCmdExe extends AbstractCmdExe<RefundTradeCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Override
    protected SingleResponse handle(RefundTradeCmd cmd) {
        tradeService.lambdaUpdate()
                .eq(MallTrade::getId,cmd.getId())
                .set(MallTrade::getRefundReason,cmd.getRefundReason())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.REFUND_EXAMINE.getCode())
                .update();
        return SingleResponse.buildSuccess();
    }
}
