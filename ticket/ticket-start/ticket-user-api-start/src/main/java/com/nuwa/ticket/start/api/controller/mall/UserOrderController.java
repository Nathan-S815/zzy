package com.nuwa.ticket.start.api.controller.mall;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.nuwa.app.ticket.command.mall.*;
import com.nuwa.client.ticket.dto.clientobject.mall.MallOrderNotifyCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.MallOrderNotifyWeChatCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.UserCreateMallTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.UserPayMallTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.MallOrderNotifyCO;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = {"文创订单-相关接口"})
@Slf4j
@RestController
@RequestMapping("/user/order")
public class UserOrderController {
    @Autowired
    private OrderNotifyCmdExe orderNotifyCmdExe;
    @Autowired
    private OrderNotifyWeChatCmdExe orderNotifyWeChatCmdExe;

/*    @ApiOperation(value = "创建订单")
    @PostMapping(value = "/order/create")
    public SingleResponse<?> create(@RequestBody UserCreateMallTradeCmd cmd, UserAware userAware) throws Exception {
        return createMallTradeCmdExe.execute(cmd);
    }*/

/*    @ApiOperation(value = "支付")
    @PostMapping(value = "/order/repay")
    public SingleResponse<?> repay(@RequestBody UserPayMallTradeCmd cmd, UserAware userAware) throws Exception {
        return payMallTradeCmdExe.execute(cmd);
    }*/

/*    @ApiOperation(value = "创建订单(微信公众号)")
    @PostMapping(value = "/wechat/order/create")
    public SingleResponse<?> createWeChat(@RequestBody UserCreateMallTradeCmd cmd, UserAware userAware) throws Exception {
        return createMallTradeWeChatCmdExe.execute(cmd);
    }

    @ApiOperation(value = "支付(微信公众号)")
    @PostMapping(value = "/wechat/order/repay")
    public SingleResponse<?> repayWeChat(UserPayMallTradeCmd cmd, UserAware userAware) throws Exception {
        return payMallTradeWeChatCmdExe.execute(cmd);
    }*/

    @ApiOperation(value = "支付回调")
    @PostMapping(value = "/order/notify")
    public String notify(MallOrderNotifyCO param) throws Exception {
        MallOrderNotifyCmd cmd = new MallOrderNotifyCmd();
        cmd.setMallOrderNotifyCO(param);
        SingleResponse resp = orderNotifyCmdExe.execute(cmd);
        return resp.getData().toString();
    }

    @ApiOperation(value = "支付回调")
    @PostMapping(value = "/wechat/order/notify")
    public String notifyWeChat(String xmlData) throws Exception {
        this.log.debug("微信支付异步通知请求参数：{}", xmlData);
        WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
        this.log.debug("微信支付异步通知请求解析后的对象：{}", result);
        MallOrderNotifyWeChatCmd cmd = new MallOrderNotifyWeChatCmd();
        BeanUtil.copyProperties(result, cmd);
        SingleResponse resp = orderNotifyWeChatCmdExe.execute(cmd);
        return resp.getData().toString();
    }

}
