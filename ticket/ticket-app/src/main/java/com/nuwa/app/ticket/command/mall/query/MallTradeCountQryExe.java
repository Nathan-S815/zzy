package com.nuwa.app.ticket.command.mall.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradeCountQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MallTradeCountQryExe extends AbstractQryExe<MallTradeCountQry, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Override
    protected SingleResponse handle(MallTradeCountQry cmd) {
        TradeNumVo vo = new TradeNumVo();
        Integer finished = tradeService.lambdaQuery()
                .eq(MallTrade::getAppId, cmd.getAppId())
                .eq(MallTrade::getMchId,cmd.getUserAware().getMchId())
                .eq(MallTrade::getClassificationFirstId,cmd.getClassificationFirstId())
                .eq(MallTrade::getOrderStatus, PaymentStatusEnum.FINISH.getCode())
                .count();
        vo.setFinished(finished);
        Integer waitForSend = tradeService.lambdaQuery()
                .eq(MallTrade::getAppId, cmd.getAppId())
                .eq(MallTrade::getMchId,cmd.getUserAware().getMchId())
                .eq(MallTrade::getClassificationFirstId,cmd.getClassificationFirstId())
                .eq(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SEND.getCode())
                .count();
        vo.setWaitForSend(waitForSend);
        Integer waitForSign = tradeService.lambdaQuery()
                .eq(MallTrade::getAppId, cmd.getAppId())
                .eq(MallTrade::getMchId,cmd.getUserAware().getMchId())
                .eq(MallTrade::getClassificationFirstId,cmd.getClassificationFirstId())
                .eq(MallTrade::getOrderStatus, PaymentStatusEnum.WAIT_FOR_SIGN.getCode())
                .count();
        vo.setWaitForSign(waitForSign);
        return SingleResponse.of(vo);
    }

    @Data
    public static class TradeNumVo {

        private Integer finished;

        private Integer waitForSend;

        private Integer waitForSign;
    }
}
