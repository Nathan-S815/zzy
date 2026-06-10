package com.nuwa.ticket.start.api.controller.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.mall.RefundApplyCmdExe;
import com.nuwa.app.ticket.command.mall.RefundNotifyCmdExe;
import com.nuwa.app.ticket.command.mall.RefundNotifyWeChatCmdExe;
import com.nuwa.client.ticket.dto.clientobject.mall.MallRefundNotifyCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.MallRefundNotifyWeChatCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.RefundApplyCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.MallRefundNotifyCO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.ticket.start.api.aop.annotation.IgnoreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"文创订单-相关接口"})
@Slf4j
@RestController
@RequestMapping("/order")
public class MallOrderController {

    @Autowired
    private RefundApplyCmdExe refundApplyCmdExe;

    @Autowired
    private RefundNotifyCmdExe refundNotifyCmdExe;

    @Autowired
    private RefundNotifyWeChatCmdExe refundNotifyWeChatCmdExe;

    @ApiOperation(value = "退款")
    @PostMapping(value = "/refund/apply")
    public SingleResponse<?> refund(RefundApplyCmd cmd, UserAware userAware) throws Exception {
        return refundApplyCmdExe.execute(cmd);
    }
}
