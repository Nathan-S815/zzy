package com.nuwa.ticket.start.dispatch.controller.notify;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.mallorder.MallOrderClientI;
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.order.param.ChannelPaymentSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.ChannelRefundSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.DouyinSettleNotifyParam;
import com.nuwa.ticket.start.dispatch.controller.notify.douying.DouYinPaymentNotifyParam;
import com.nuwa.ticket.start.dispatch.controller.notify.douying.DouYinNotifyRetVO;
import com.nuwa.ticket.start.dispatch.controller.notify.douying.DouYinRefundNotifyParam;
import com.nuwa.ticket.start.dispatch.controller.notify.douying.DouYinSettleNotifyParam;
import com.nuwa.ticket.start.dispatch.controller.notify.gateway.GatewayPaymentNotifyParam;
import com.nuwa.ticket.start.dispatch.controller.notify.gateway.GatewayRefundNotifyParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hy
 */
@Slf4j
@RestController
@Api(tags = {"支付通道回调"})
public class ChannelNotifyController {

    @Autowired
    private OrderClientI orderClientI;

    @Autowired
    private MallOrderClientI mallOrderClientI;

    @ApiOperation(value = "支付成功")
    @RequestMapping(value = "/douyin/paySuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public DouYinNotifyRetVO paySuccess(@RequestBody DouYinPaymentNotifyParam param) {
        log.info("DouYinPaymentNotifyParam param:{}", param);
        DouYinNotifyRetVO vo = new DouYinNotifyRetVO();
        ChannelPaymentSuccessNotifyParam dto = new ChannelPaymentSuccessNotifyParam();
        DouYinPaymentNotifyParam.Body body = param.toBody();
        dto.setOrderNo(body.getCp_orderno());
        try {
            SingleResponse<?> singleResponse = orderClientI.paymentSuccess(dto);
            if (singleResponse.isSuccess()) {
                vo.setErr_no(0);
                vo.setErr_tips(DouYinNotifyRetVO.SUCCESS);
            }
        } catch (Exception ex) {
            log.error("douyinSettleNotify error", ex);
            return vo;
        }
        return vo;
    }

    @ApiOperation(value = "支付成功")
    @RequestMapping(value = "/douyin/wenchuangPaySuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public DouYinNotifyRetVO wenchuangPaySuccessNotify(@RequestBody DouYinPaymentNotifyParam param) {
        log.info("douyin wenchuangPaySuccessNotify param:{}", param);
        DouYinNotifyRetVO vo = new DouYinNotifyRetVO();
        ChannelPaymentSuccessNotifyParam dto = new ChannelPaymentSuccessNotifyParam();
        DouYinPaymentNotifyParam.Body body = param.toBody();
        dto.setOrderNo(body.getCp_orderno());
        try {
            SingleResponse<?> singleResponse = mallOrderClientI.paymentSuccess(dto);
            if (singleResponse.isSuccess()) {
                vo.setErr_no(0);
                vo.setErr_tips(DouYinNotifyRetVO.SUCCESS);
            }
        } catch (Exception ex) {
            log.error("douyin wenchuangPaySuccessNotify error", ex);
            return vo;
        }
        return vo;
    }

    @ApiOperation(value = "抖音结算回调处理")
    @RequestMapping(value = "/douyin/settleNotify", method = RequestMethod.POST)
    @ResponseBody
    public DouYinNotifyRetVO douyinSettleNotify(@RequestBody DouYinSettleNotifyParam param) {
        log.info("douyinSettleNotify param:{}", param);
        //DouYinSettleNotifyParam(timestamp=1643161456, nonce=8263, type=settle, msg={"appid":"tta4f5d2483bcc5c3a01","cp_settle_no":"1483253818979385344","cp_extra":"","status":"SUCCESS","rake":0,"commission":0,"settle_detail":"商户号70270395621615393650-分成金额(分)1","settled_at":1643161456,"message":"SUCCESS","order_id":"7054355287771482401"}, msg_signature=a09aeafa134f195c3bca456c7b6289f1b664ad48)
        DouYinNotifyRetVO vo = new DouYinNotifyRetVO();
        DouyinSettleNotifyParam dto = new DouyinSettleNotifyParam();
        DouYinSettleNotifyParam.Body body = param.toBody();
        dto.setOrderNo(body.getCp_settle_no());
        dto.setStatus(body.getStatus());
        dto.setSettledAt(body.getSettled_at());
        try {
            SingleResponse<?> singleResponse = orderClientI.douyinSettleProcess(dto);
            if (singleResponse.isSuccess()) {
                vo.setErr_no(0);
                vo.setErr_tips(DouYinNotifyRetVO.SUCCESS);
            }
        } catch (Exception ex) {
            log.error("douyinSettleNotify error", ex);
            return vo;
        }
        return vo;
    }

    @ApiOperation(value = "退款成功")
    @RequestMapping(value = "/douyin/refundSuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public DouYinNotifyRetVO refundSuccess(@RequestBody DouYinRefundNotifyParam param) {
        log.info("DouYinRefundNotifyParam param:{}", param);
        DouYinNotifyRetVO vo = new DouYinNotifyRetVO();
        DouYinRefundNotifyParam.Body body = param.toBody();
        ChannelRefundSuccessNotifyParam dto = new ChannelRefundSuccessNotifyParam();
        dto.setOrderNo(body.getCp_refundno());
        try {
            SingleResponse<?> singleResponse = orderClientI.refundSuccess(dto);
            if (singleResponse.isSuccess()) {
                vo.setErr_no(0);
                vo.setErr_tips(DouYinNotifyRetVO.SUCCESS);
            }
        } catch (Exception ex) {
            log.error("refundSuccess error", ex);
            return vo;
        }
        return vo;
    }

    @ApiOperation(value = "抖音文创退款成功")
    @RequestMapping(value = "/douyin/wenchuangRefundSuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public DouYinNotifyRetVO wenchuangRefundSuccessNotify(@RequestBody DouYinRefundNotifyParam param) {
        log.info("DouYinRefundNotifyParam param:{}", param);
        DouYinNotifyRetVO vo = new DouYinNotifyRetVO();
        DouYinRefundNotifyParam.Body body = param.toBody();
        ChannelRefundSuccessNotifyParam dto = new ChannelRefundSuccessNotifyParam();
        dto.setOrderNo(body.getCp_refundno());
        try {
            SingleResponse<?> singleResponse = mallOrderClientI.refundSuccess(dto);
            if (singleResponse.isSuccess()) {
                vo.setErr_no(0);
                vo.setErr_tips(DouYinNotifyRetVO.SUCCESS);
            }
        } catch (Exception ex) {
            log.error("refundSuccess error", ex);
            return vo;
        }
        return vo;
    }

    @ApiOperation(value = "文创订单支付成功回调处理")
    @RequestMapping(value = "/gateway/wenchuang/paySuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public String wenchuangGatewayPaySuccess(GatewayPaymentNotifyParam param) {
        log.info("wenchuangGatewayPaySuccess param:{}", param);
        ChannelPaymentSuccessNotifyParam dto = new ChannelPaymentSuccessNotifyParam();
        dto.setOrderNo(param.getOrderNo());
        try {
            SingleResponse<?> singleResponse = mallOrderClientI.paymentSuccess(dto);
            if (singleResponse.isSuccess()) {
                return "ret=success";
            }
        } catch (Exception ex) {
            log.error("gatewayPaySuccess error", ex);
            return "failed";
        }
        return "failed";
    }

    @ApiOperation(value = "网关支付成功")
    @RequestMapping(value = "/gateway/paySuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public String gatewayPaySuccess(GatewayPaymentNotifyParam param) {
        log.info("GatewayPaymentNotifyParam param:{}", param);
        ChannelPaymentSuccessNotifyParam dto = new ChannelPaymentSuccessNotifyParam();
        dto.setOrderNo(param.getOrderNo());
        try {
            SingleResponse<?> singleResponse = orderClientI.paymentSuccess(dto);
            if (singleResponse.isSuccess()) {
                return "ret=success";
            }
        } catch (Exception ex) {
            log.error("gatewayPaySuccess error", ex);
            return "failed";
        }
        return "failed";
    }

    @ApiOperation(value = "网关支付成功")
    @RequestMapping(value = "/gateway/refundSuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public String gatewayRefundSuccess(GatewayRefundNotifyParam param) {
        log.info("gatewayRefundSuccess param:{}", param);
        ChannelRefundSuccessNotifyParam dto = new ChannelRefundSuccessNotifyParam();
        dto.setOrderNo(param.getOrderNo());
        try {
            SingleResponse<?> singleResponse = orderClientI.refundSuccess(dto);
            if (singleResponse.isSuccess()) {
                return "ret=success";
            }
        } catch (Exception ex) {
            log.error("gatewayRefundSuccess error", ex);
            return "failed";
        }
        return "failed";
    }

    @ApiOperation(value = "文创退款成功回调处理")
    @RequestMapping(value = "/gateway/wenchuang/refundSuccessNotify", method = RequestMethod.POST)
    @ResponseBody
    public String wenchuangGatewayRefundSuccess(GatewayRefundNotifyParam param) {
        log.info("gatewayRefundSuccess param:{}", param);
        ChannelRefundSuccessNotifyParam dto = new ChannelRefundSuccessNotifyParam();
        dto.setOrderNo(param.getOrderNo());
        try {
            SingleResponse<?> singleResponse = mallOrderClientI.refundSuccess(dto);
            if (singleResponse.isSuccess()) {
                return "ret=success";
            }
        } catch (Exception ex) {
            log.error("gatewayRefundSuccess error", ex);
            return "failed";
        }
        return "failed";
    }
}
