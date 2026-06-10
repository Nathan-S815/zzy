package com.nuwa.app.ticket.command.mall;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.UserRefundTradeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallMessage;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.MallMessageService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserRefundMallTradeCmdExe extends AbstractCmdExe<UserRefundTradeCmd, SingleResponse> {

    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private MallProductService productService;

    @Autowired
    private MallMessageService messageService;

    @Override
    protected SingleResponse handle(UserRefundTradeCmd cmd) {
        boolean update = tradeService.lambdaUpdate()
                .eq(MallTrade::getId, cmd.getId())
                .set(MallTrade::getRefundReason, cmd.getRefundReason())
                .set(MallTrade::getRefundTime, new Date())
                .set(MallTrade::getOrderStatus, PaymentStatusEnum.REFUND_EXAMINE.getCode())
                .update();

        if (update){
            Long productId = tradeService.getById(cmd.getId()).getProductId();
            MallProduct product = productService.getById(productId);
           /* MallMessage message = new MallMessage();
            BeanUtil.copyProperties(cmd,message);
            message.setBizCode("退款");
            message.setReceivePhone(product.getServicePhone());
            message.setMchId(cmd.getUserAware().getMchId());
            message.setAppId(cmd.getUserAware().getMchAppId());
            messageService.save(message);*/
        }

        return SingleResponse.buildSuccess();
    }
}
