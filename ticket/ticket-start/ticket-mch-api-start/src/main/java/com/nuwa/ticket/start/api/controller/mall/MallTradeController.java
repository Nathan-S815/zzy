package com.nuwa.ticket.start.api.controller.mall;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.mall.RefundMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.SendMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.SendOfflineMallTradeCmdExe;
import com.nuwa.app.ticket.command.mall.query.MallTradeCountQryExe;
import com.nuwa.app.ticket.command.mall.query.MallTradePageQryExe;
import com.nuwa.app.ticket.command.mall.query.MallTradeQryExe;
import com.nuwa.client.ticket.dto.clientobject.mall.RefundTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.SendOfflineTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.SendTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradeCountQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradePageQry;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallTradeQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ROOT
 * @since 2021-02-05
 */
@Api(tags = {"文创交易操作-相关接口"})
@Slf4j
@RestController
@RequestMapping("/trade")
public class MallTradeController {

    @Autowired
    private MallTradeQryExe mallTradeQryExe;

    @Autowired
    private MallTradePageQryExe mallTradePageQryExe;

    @Autowired
    private MallTradeCountQryExe mallTradeCountQryExe;

    @Autowired
    private SendMallTradeCmdExe sendMallTradeCmdExe;

    @Autowired
    private SendOfflineMallTradeCmdExe sendOfflineMallTradeCmdExe;

    @Autowired
    private RefundMallTradeCmdExe refundMallTradeCmdExe;

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(MallTradeQry cmd, UserAware userAware) throws Exception {
        return mallTradeQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(MallTradePageQry cmd, UserAware userAware) throws Exception {
        return mallTradePageQryExe.execute(cmd);
    }

    @ApiOperation(value = "数量查询")
    @GetMapping(value = "/count")
    public SingleResponse<?> count(MallTradeCountQry cmd, UserAware userAware) throws Exception {
        return mallTradeCountQryExe.execute(cmd);
    }

    @ApiOperation(value = "商家发货")
    @PostMapping(value = "/send")
    public SingleResponse<?> send(@RequestBody SendTradeCmd cmd, UserAware userAware) throws Exception {
        return sendMallTradeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "线下发货")
    @GetMapping(value = "/offline")
    public SingleResponse<?> offline(SendOfflineTradeCmd cmd, UserAware userAware) throws Exception {
        return sendOfflineMallTradeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "退款申请")
    @GetMapping(value = "/refund")
    public SingleResponse<?> refund(RefundTradeCmd cmd, UserAware userAware) throws Exception {
        return refundMallTradeCmdExe.execute(cmd);
    }

}
