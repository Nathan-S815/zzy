package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.SendOfflineTradeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendOfflineMallTradeCmdExe extends AbstractCmdExe<SendOfflineTradeCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Override
    protected SingleResponse handle(SendOfflineTradeCmd cmd) {
        tradeService.lambdaUpdate()
                .eq(MallTrade::getId,cmd.getId())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SIGN.getCode())
                .update();
        return SingleResponse.buildSuccess();
    }
}
