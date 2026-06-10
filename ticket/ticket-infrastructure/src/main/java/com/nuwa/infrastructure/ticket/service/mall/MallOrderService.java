package com.nuwa.infrastructure.ticket.service.mall;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.cola.exception.BizException;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.client.ticket.util.ChannelResultCodeEnum;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallTrade;
import com.nuwa.infrastructure.ticket.database.mall.service.*;
import com.nuwa.infrastructure.ticket.database.manager.dto.ChannelRefundAcceptFailedDTO;
import com.nuwa.infrastructure.ticket.database.manager.dto.ChannelRefundAcceptSuccessDTO;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.service.log.dto.OrderLogDTO;
import com.nuwa.infrastructure.ticket.service.log.enums.TicketOrderLogTypeEnum;
import com.nuwa.infrastructure.ticket.service.mall.dto.MallRefundOrderDTO;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinSender;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateRefundReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateRefundResp;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppOrderRefundReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.MiniAppOrderRefundResp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * IntegralManageService 积分订单管理
 *
 * @author hy
 * @date 2021/5/2 17:08
 * @since 1.0.0
 */
@Slf4j
@Service
public class MallOrderService {
    @Autowired
    private MallTradeService tradeService;

    @Autowired
    private MallProductService productService;

    @Autowired
    private MallProductSkuStockService productSkuStockService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MallPaymentOrderService paymentOrderService;

    @Autowired
    private MallTradeService mallTradeService;

    @Autowired
    private MallMessageService messageService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${gateway.refund.wenchuang.notify.url}")
    private String gatewayRefundWenchuangNotify;

    @Value("${douyin.refund.wenchuang.notify.url}")
    private String douYinRefundWenchuangNotify;

    @Transactional(rollbackFor = BizException.class)
    public SingleResponse refund(MallRefundOrderDTO dto) {
        MallTrade trade = tradeService.getById(dto.getTradeId());
        MerchantAppPayConf payConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getId, trade.getAppId()).last("limit 1").one();
        Assert.isTrue(Objects.nonNull(payConf), "支付配置错误");
        String refundSerialNo = RandomUtil.randomNumbers(20);
        boolean update = mallTradeService.lambdaUpdate()
                .set(MallTrade::getRefundSerialNo, refundSerialNo)
                .eq(MallTrade::getId, trade.getId())
                .update();
        if (payConf.getChannelType().equalsIgnoreCase("douyin")) {
            CreateRefundReq req = new CreateRefundReq();
            DouYinPayConfig config = new DouYinPayConfig();
            config.setSalt(payConf.getSalt());
            req.setConfig(config);
            req.setAppId(payConf.getOutAppId());
            req.setBody("退款");
            req.setSubject("退款");
            req.setTotalAmount(new BigDecimal(trade.getTotalAmount()).multiply(new BigDecimal("100")).longValue());
            req.setOutOrderNo(trade.getOrderNo() + "");
            req.setOutRefundNo(refundSerialNo);
            req.setAllSettle(0);
            req.setReason("退款");
            req.setNotifyUrl(douYinRefundWenchuangNotify);
            try {
                ChannelResult<CreateRefundResp> channelResult = DouYinSender.createRefund(req);
                if (channelResult.isSuccessful()) {
                    //退款成功
                    mallTradeService.lambdaUpdate()
                            .set(MallTrade::getOrderStatus, PaymentStatusEnum.REFUNDING.getCode())
                            .eq(MallTrade::getId, trade.getId())
                            .update();

                } else if (channelResult.getCode().equals(ChannelResultCodeEnum.TIMEOUT_ERROR.getCode())) {
                    log.warn("退款请求通道超时,trade:{}", trade);
                    //todo 退款超时处理
                } else {
                    //退款申请失败
                    log.info("退款失败,trade:{}", trade);

                    mallTradeService.lambdaUpdate()
                            .set(MallTrade::getOrderStatus, PaymentStatusEnum.FAILED_REFUND.getCode())
                            .eq(MallTrade::getId, trade.getId())
                            .update();
                }
            } catch (Exception ex) {
                log.error("orderNo:{},请求发生异常", trade.getRefundOrderNo(), ex);
            }
        } else {
            MiniAppOrderRefundReq refundReq = new MiniAppOrderRefundReq();
            RequestHead head = new RequestHead(payConf.getChannelMchId(), new Date());
            refundReq.setHead(head);
            MiniAppOrderRefundReq.Body body = new MiniAppOrderRefundReq.Body();
            body.setOrderNo(refundSerialNo);
            body.setRefundReason(trade.getRefundReason());
            body.setOrigMchOrderNo(trade.getMchOrderNo());
            body.setRefundAmount(dto.getRefundAmount().toString());
            body.setNotifyUrl(gatewayRefundWenchuangNotify);
            refundReq.setBody(body);
            String sign = refundReq.buildSign(payConf.getSalt());
            System.out.println(sign);
            head.setSign(sign);
            //请求并返回结果

            ChannelResult<MiniAppOrderRefundResp> refundResult = PaymentGatewaySender.refundOrder(refundReq, "http://apipayment.zhongzhiyou.cn");
            if (Objects.nonNull(refundResult) && refundResult.isSuccessful()) {
                trade.setOrderStatus(PaymentStatusEnum.REFUNDING.getCode());
                boolean flag = tradeService.updateById(trade);
                if (flag) {
                    log.info("退款中 orderNo:[{}]", trade.getTradeNo());
                    return SingleResponse.of(refundResult.getData());
                } else {
                    throw new RuntimeException();
                }
            }
        }


        return ErrorEnum.REFUND_ORDER_FAILED.buildFailure();
    }

    @Data
    public static class PayOrderVO {
        private Object payParams;
        private MallTrade trade;
    }

}
