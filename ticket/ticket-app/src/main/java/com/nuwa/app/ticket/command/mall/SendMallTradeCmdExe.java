package com.nuwa.app.ticket.command.mall;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.SendTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.SendTradeCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.express.entity.Express;
import com.nuwa.infrastructure.ticket.database.express.service.ExpressService;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendMallTradeCmdExe extends AbstractCmdExe<SendTradeCmd, SingleResponse> {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private MallTradeService tradeService;

    @Override
    protected SingleResponse handle(SendTradeCmd cmd) {
        SendTradeCO co = cmd.getSendTradeCO();
        Express express = new Express();
        BeanUtil.copyProperties(co,express);
        expressService.save(express);
        tradeService.lambdaUpdate()
                .eq(MallTrade::getId,cmd.getId())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SIGN.getCode())
                .set(MallTrade::getExpressId,express.getId())
                .update();
        return SingleResponse.of(express);
    }
}
