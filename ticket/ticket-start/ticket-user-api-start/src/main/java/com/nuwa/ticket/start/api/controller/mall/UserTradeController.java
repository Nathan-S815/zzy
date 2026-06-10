package com.nuwa.ticket.start.api.controller.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.mall.UserCancelMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.UserReceiveMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.UserRefundMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.query.UserMallTradePageQryExe;
import com.nuwa.app.ticket.command.mall.query.UserMallTradeQryExe;
import com.nuwa.client.ticket.dto.clientobject.mall.UserCancelTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.UserReceiveTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.UserRefundTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallTradePageQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.UserMallTradeQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2021-02-05
 */
@Api(tags = {"文创交易订单-相关接口"})
@Slf4j
@RestController
@RequestMapping("/user/trade")
public class UserTradeController {

    @Autowired
    private UserMallTradeQryExe userMallTradeQryExe;

    @Autowired
    private UserMallTradePageQryExe userMallTradePageQryExe;

    @Autowired
    private UserRefundMallTradeCmdExe userRefundMallTradeCmdExe;

    @Autowired
    private UserReceiveMallTradeCmdExe userReceiveMallTradeCmdExe;

    @Autowired
    private UserCancelMallTradeCmdExe userCancelMallTradeCmdExe;

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(UserMallTradeQry cmd, UserAware userAware) throws Exception {
        return userMallTradeQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(UserMallTradePageQry cmd, UserAware userAware) throws Exception {
        return userMallTradePageQryExe.execute(cmd);
    }

    @ApiOperation(value = "退款申请")
    @GetMapping(value = "/refund")
    public SingleResponse<?> refund(UserRefundTradeCmd cmd, UserAware userAware) throws Exception {
        return userRefundMallTradeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "确认收货")
    @GetMapping(value = "/receive")
    public SingleResponse<?> receive(UserReceiveTradeCmd cmd, UserAware userAware) throws Exception {
        return userReceiveMallTradeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "取消订单")
    @GetMapping(value = "/cancel")
    public SingleResponse<?> cancel(UserCancelTradeCmd cmd, UserAware userAware) throws Exception {
        return userCancelMallTradeCmdExe.execute(cmd);
    }

}
