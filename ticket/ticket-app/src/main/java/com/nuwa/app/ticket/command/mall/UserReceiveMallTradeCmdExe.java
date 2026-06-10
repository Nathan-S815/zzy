package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.UserReceiveTradeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserReceiveMallTradeCmdExe extends AbstractCmdExe<UserReceiveTradeCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Override
    protected SingleResponse handle(UserReceiveTradeCmd cmd) {
        tradeService.lambdaUpdate()
                .eq(MallTrade::getId,cmd.getId())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.FINISH.getCode())
                .set(MallTrade::getFinishTime,new Date())
                .update();
        return SingleResponse.buildSuccess();
    }
}
