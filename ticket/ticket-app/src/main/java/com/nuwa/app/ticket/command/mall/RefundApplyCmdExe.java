package com.nuwa.app.ticket.command.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.RefundApplyCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.service.mall.MallOrderService;
import com.nuwa.infrastructure.ticket.service.mall.dto.MallRefundOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class RefundApplyCmdExe extends AbstractCmdExe<RefundApplyCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private MallOrderService mallOrderService;


    @Override
    protected SingleResponse handle(RefundApplyCmd cmd) {
        MallTrade trade = tradeService.getById(cmd.getId());
        if (Objects.isNull(trade)) {
            return ErrorEnum.TRADE_NOT_EXIST.buildFailure();
        }
        if (trade.getOrderStatus()!=14){
            return ErrorEnum.ORDER_STATUS_ERROR.buildFailure();
        }
        MallRefundOrderDTO dto = new MallRefundOrderDTO();
        dto.setTradeId(cmd.getId());
        dto.setRefundAmount(cmd.getRefundAmount());
        dto.setMchAppId(cmd.getUserAware().getMchAppId());
        dto.setMchId(cmd.getUserAware().getMchId());
        return mallOrderService.refund(dto);
    }
}
