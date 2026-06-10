package com.nuwa.ticket.start.api.controller.mall;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.cola.extension.BizScenario;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.ticket.command.PlaceOrderCmdExe;
import com.nuwa.app.ticket.command.query.order.UserRefundOrderPageJoinQry;
import com.nuwa.app.ticket.constant.ExtensionConstant;
import com.nuwa.client.ticket.api.mallorder.MallOrderClientI;
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.order.param.ChannelPaymentSuccessNotifyParam;
import com.nuwa.client.ticket.api.order.param.WenChuangOrderCancelDelayJobParam;
import com.nuwa.client.ticket.dto.clientobject.mall.UserCreateMallTradeCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.CreateMallTradeCO;
import com.nuwa.client.ticket.dto.clientobject.order.PlaceOrderCmd;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserTicketOrderPageQry;
import com.nuwa.client.ticket.dto.domainevent.order.SupplierApplyRefundEvent;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.ticket.database.mall.entity.*;
import com.nuwa.infrastructure.ticket.database.mall.service.MallPaymentOrderService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallTradeService;
import com.nuwa.infrastructure.ticket.database.manager.AlipayDataManager;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.member.service.ThirdUserService;
import com.nuwa.infrastructure.ticket.database.order.entity.*;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.UserRefundOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserRefundPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.param.UserTicketOrderPageParam;
import com.nuwa.infrastructure.ticket.database.order.service.*;
import com.nuwa.infrastructure.ticket.database.order.vo.UserRefundOrderPageVO;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.enums.PaymentStatusEnum;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.lock.DistributedLocker;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.service.mall.MallOrderService;
import com.nuwa.infrastructure.ticket.service.mall.dto.OrderTypeEnum;
import com.nuwa.infrastructure.ticket.service.mall.dto.PayTypeEnum;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinSender;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateOrderReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateOrderResp;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppOrderCreateReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.MiniAppOrderCreateResp;
import com.nuwa.ticket.start.api.biz.UserTradeManager;
import com.nuwa.ticket.start.api.biz.dto.ChannelCreateOrderDTO;
import com.nuwa.ticket.start.api.biz.dto.ChannelCreateOrderSuccessDTO;
import com.nuwa.ticket.start.api.biz.dto.UserApplyRefundDTO;
import com.nuwa.ticket.start.api.biz.ret.RefundApplyRet;
import com.nuwa.ticket.start.api.constants.RedisKeyConstant;
import com.nuwa.ticket.start.api.controller.dto.EntranceCertificateVO;
import com.nuwa.ticket.start.api.controller.dto.ProductRefundRuleConfigDTO;
import com.nuwa.ticket.start.api.controller.dto.ProductVerificationRuleConfigDTO;
import com.nuwa.ticket.start.api.controller.dto.TouristDTO;
import com.nuwa.ticket.start.api.controller.order.param.GetOrderRefundRuleParam;
import com.nuwa.ticket.start.api.controller.order.param.OrderCancelParam;
import com.nuwa.ticket.start.api.controller.order.param.OrderPaymentParam;
import com.nuwa.ticket.start.api.controller.order.param.OrderRefundParam;
import com.nuwa.ticket.start.api.controller.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("wenchuang/order")
@Api(tags = {"文创订单相关"})
public class MallOrderController {

    @Autowired
    private ScenicspotBaseTypeService scenicspotBaseTypeService;

    @Autowired
    private ScenicspotTypeService scenicspotTypeService;

    @Autowired
    private ScenicspotLabelService scenicspotLabelService;

    @Autowired
    private ScenicspotMaterialService scenicspotMaterialService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ScenicspotJoinMapper scenicspotJoinMapper;

    @Autowired
    private MerchantCanSelectScenicspotJoinMapper merchantCanSelectScenicspotJoinMapper;

    @Autowired
    private UserScenicspotJoinMapper userScenicspotJoinMapper;

    @Autowired
    private MerchantScenicspotPoiService merchantScenicspotPoiService;

    @Autowired
    private ScenicspotBaseLabelService scenicspotBaseLabelService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private MerchantProductJoinMapper merchantProductJoinMapper;

    @Autowired
    private UserRefundOrderJoinMapper userRefundOrderJoinMapper;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ScenicspotProductBookRuleConfigService scenicspotProductBookRuleConfigService;

    @Autowired
    private ScenicspotProductVerificationConfigService scenicspotProductVerificationConfigService;

    @Autowired
    private ScenicspotProductRefundRuleConfigService scenicspotProductRefundRuleConfigService;

    @Autowired
    private ScenicspotProductValidPeriodConfigService scenicspotProductValidPeriodConfigService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private SupplierPaymentOrderService supplierPaymentOrderService;

    @Autowired
    private ChannelPaymentOrderService channelPaymentOrderService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private OrderVoucherService orderVoucherService;

    @Autowired
    private TicketRefundService ticketRefundService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Value("${douyin.wenchuang.payment.notify.url}")
    private String douyinWenChuangPaymentNotifyUrl;

    @Autowired
    private UserTradeManager userTradeManager;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private DistributedLocker distributedLocker;

    @Autowired
    private LogBizService logBizService;

    @Autowired
    private PlaceOrderCmdExe placeOrderCmdExe;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThirdUserService thirdUserService;

    @Value("${gateway.payment.notify.url}")
    private String gatewayPaymentNotify;

    @Value("${gateway.payment.wenchuang.notify.url}")
    private String gatewayPaymentWenchuangNotify;

    @Autowired
    private OrderClientI orderClientI;

    @Autowired
    private AlipayDataManager alipayDataManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private MallProductSkuStockService productSkuStockService;

    @Autowired
    private MallPaymentOrderService mallPaymentOrderService;

    @Autowired
    private MallTradeService mallTradeService;

    @Autowired
    private MallOrderClientI mallOrderClientI;

    @ApiOperation(value = "下单")
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> placeOrder(@RequestBody @Valid UserCreateMallTradeCmd cmd, UserAware userAware) {
        log.info("createOrder params:{}", JSONUtil.toJsonPrettyStr(cmd));
        CreateMallTradeCO createCO = cmd.getCreateMallTradeCO();
        Member member = memberService.getById(cmd.getUserAware().getUserId());

        MerchantAppPayConf payConf;
        payConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getOutAppId, createCO.getAppId()).last("limit 1").one();
        Assert.isTrue(Objects.nonNull(payConf), "支付配置错误");

        MallProduct product = mallProductService.getById(createCO.getProductId());
        //判断是否可以购买
        MallProductSkuStock productSkuStock = null;
        productSkuStock = productSkuStockService.getById(createCO.getSpecificationsId());
        //判断是否可以购买
        if (Objects.nonNull(productSkuStock)) {
            if (productSkuStock.getStock() < createCO.getProductNum()) {
                return ErrorEnum.NOT_ENOUGH.buildFailure();
            }
            productSkuStock.setStock(productSkuStock.getStock() - createCO.getProductNum());
            productSkuStockService.updateById(productSkuStock);
        }

        if (Objects.nonNull(productSkuStock)) {
            String tradeNo = RandomUtil.randomNumbers(20);
            String subject = product.getProductName();
            String bodyS = "购买-" + product.getProductName();
            Date payTimeOut = DateUtil.offsetMinute(new Date(), 15);

            MallPaymentOrder paymentOrder = new MallPaymentOrder();
            paymentOrder.setMchId(member.getMchId());
            paymentOrder.setAppId(payConf.getId());
            paymentOrder.setTradeNo(tradeNo);
            paymentOrder.setPaymentOrderNo(tradeNo);
            paymentOrder.setMemberId(cmd.getUserAware().getUserId());
            paymentOrder.setTotalAmount(productSkuStock.getSellPrice() * createCO.getProductNum());
            paymentOrder.setPayAmount(productSkuStock.getSellPrice() * createCO.getProductNum());
            paymentOrder.setSubject(subject);
            paymentOrder.setBody(bodyS);
            paymentOrder.setTradeStatus(PaymentStatusEnum.CREATE.getCode());
            mallPaymentOrderService.save(paymentOrder);

            //创建paymentOrder  init
            MallTrade trade = new MallTrade();
            trade.setMchId(member.getMchId());
            trade.setAppId(payConf.getId());
            trade.setTradeNo(tradeNo);
            trade.setOrderNo(paymentOrder.getPaymentOrderNo());
            trade.setMemberId(userAware.getUserId());
            trade.setPayAccount(payConf.getChannelMchId());
            trade.setProductId(createCO.getProductId());
            trade.setProductName(product.getProductName());
            trade.setSpecificationsId(createCO.getSpecificationsId());
            trade.setProductNum(createCO.getProductNum());
            trade.setConsigneeName(createCO.getConsigneeName());
            trade.setReceivingMethod(createCO.getReceivingMethod());
            if (trade.getReceivingMethod() == 10) {
                trade.setConsigneeTel(createCO.getConsigneeTel());
                trade.setConsigneeAddr(createCO.getConsigneeAddr());
                trade.setConsigneeAddr(createCO.getConsigneeAddr());
            } else {
                trade.setStoreAddress(createCO.getStoreAddress());
                trade.setLongitude(createCO.getLongitude());
                trade.setLatitude(createCO.getLatitude());
            }
            trade.setTotalAmount(productSkuStock.getSellPrice() * createCO.getProductNum());
            trade.setPayAmount(productSkuStock.getSellPrice() * createCO.getProductNum());
            trade.setPayStatus(PaymentStatusEnum.CREATE.getCode());
            trade.setOrderType(OrderTypeEnum.DIRECT_SELLING.getCode());
            trade.setOrderStatus(PaymentStatusEnum.WAIT_FOR_PAY.getCode());
            trade.setExpireTime(DateUtil.offsetMinute(new Date(), 15));
            trade.setClassificationFirstId(product.getClassificationFirstId());
            if (payConf.getChannelType().equalsIgnoreCase("douyin")) {
                trade.setPayType(20);
            } else {
                trade.setPayType(10);
            }
            mallTradeService.save(trade);

            MallOrderService.PayOrderVO payOrderVO = new MallOrderService.PayOrderVO();
            //payOrderVO.setPayParams(data.getPayParams());
            payOrderVO.setTrade(trade);
            return SingleResponse.of(payOrderVO);

           /* MerchantAppBaseConf appBaseConf = merchantAppBaseConfService.getById(payConf.getMchClientId());
            String payType = createCO.getPayType();
            ThirdUser thirdUser = thirdUserService.lambdaQuery()
                    .eq(ThirdUser::getChannelCode, payType)
                    .eq(ThirdUser::getUserId, userAware.getUserId())
                    .one();
            Assert.notNull(thirdUser);
            MiniAppOrderCreateReq createReq = new MiniAppOrderCreateReq();
            RequestHead head = new RequestHead(payConf.getChannelMchId(), new Date());
            createReq.setHead(head);
            MiniAppOrderCreateReq.Body body = new MiniAppOrderCreateReq.Body();
            body.setAppId(createCO.getAppId());
            body.setOrderNo(paymentOrder.getPaymentOrderNo() + "");
            body.setBuyerId(thirdUser.getOpenId());
            body.setClientIp(userAware.getHostIp());
            body.setSubject(paymentOrder.getSubject());
            body.setTotalAmount(paymentOrder.getTotalAmount());
            body.setNotifyUrl(gatewayPaymentWenchuangNotify);
            body.setTxnTimeOut(DateUtil.offsetMinute(new Date(), 30).getTime() + "");
            body.setTenementId(userAware.getMchId() + "");
            body.setBody(paymentOrder.getBody());
            if (StrUtil.isNotEmpty(appBaseConf.getAppAuthCode())) {
                JSONObject extendObj = new JSONObject();
                extendObj.putOpt("appAuthToken", appBaseConf.getAppAuthCode());
                body.setExtend(JSONUtil.toJsonStr(extendObj));
            }
            createReq.setBody(body);
            String sign = createReq.buildSign(payConf.getSalt());
            head.setSign(sign);
            ChannelResult<MiniAppOrderCreateResp> payResult = PaymentGatewaySender.createOrder(createReq, "http://apipayment.zhongzhiyou.cn");
            if (Objects.nonNull(payResult) && payResult.isSuccessful()) {
                MiniAppOrderCreateResp data = payResult.getData();
                if (Objects.nonNull(data)) {
                    boolean update = mallTradeService.lambdaUpdate()
                            .eq(MallTrade::getTradeNo, trade.getTradeNo())
                            .set(MallTrade::getPayChannel, trade.getPayChannel())
                            .set(MallTrade::getMchOrderNo, paymentOrder.getPaymentOrderNo())
                            .update();
                    if (update) {
                        log.info("下单完成 orderNo:[{}]", trade.getTradeNo());
                    }
                    MallOrderService.PayOrderVO payOrderVO = new MallOrderService.PayOrderVO();
                    payOrderVO.setPayParams(data.getPayParams());
                    payOrderVO.setTrade(trade);

                    return SingleResponse.of(payOrderVO);
                }
            }*/
        }
        return SingleResponse.buildFailure("9874", "下单失败");
    }

    @ApiOperation(value = "支付")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> payment(@RequestBody @Valid OrderPaymentParam param, UserAware userAware) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        MallTrade mallTrade = mallTradeService.getById(param.getOrderId());
        MallPaymentOrder mallPaymentOrder = mallPaymentOrderService.lambdaQuery().eq(MallPaymentOrder::getTradeNo, mallTrade.getTradeNo()).one();
        Assert.notNull(mallPaymentOrder, "订单不存在");
        // Assert.isTrue(!mallPaymentOrder.getTradeStatus().equals(10), "当前状态不允许支付");

        String tradeNo = mallPaymentOrder.getPaymentOrderNo();

        MerchantAppPayConf payConf;
        payConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getOutAppId, param.getAppId()).last("limit 1").one();
        Assert.isTrue(Objects.nonNull(payConf), "支付配置错误");

        SingleResponse<?> payParamVO = null;
        if ("weixin_mini".equals(param.getPayType()) || "weixin_mp".equalsIgnoreCase(param.getPayType())) {
            payParamVO = doGatewayMiniPay(param, userAware, mallTrade, payConf, mallPaymentOrder);
        } else if ("alipay_mini".equals(param.getPayType())) {
            log.info(">>>> alipay_mini");
            payParamVO = doGatewayMiniPay(param, userAware, mallTrade, payConf, mallPaymentOrder);
        } else if ("alipay_mini_template".equals(param.getPayType())) {
            log.info(">>>> alipay_mini");
            payParamVO = doGatewayMiniPay(param, userAware, mallTrade, payConf, mallPaymentOrder);
        } else if ("douyin_mini".equals(param.getPayType())) {
            payParamVO = doDouYinPayment(param, userAware, mallTrade, payConf, mallPaymentOrder);
        }
        if (payParamVO != null) {
            return payParamVO;
        }

        stopWatch.stop();
        log.info("cost time:{} ms", stopWatch.getTotalTimeMillis());
        return SingleResponse.buildFailure("9863", "支付失败");
    }

    private SingleResponse<?> doGatewayMiniPay(OrderPaymentParam param, UserAware userAware, MallTrade
            mallTrade, MerchantAppPayConf payConf, MallPaymentOrder channelPaymentOrder) {
        Long mchClientId = payConf.getMchClientId();
        MerchantAppBaseConf appBaseConf = merchantAppBaseConfService.getById(mchClientId);
        String payType = param.getPayType();
        ThirdUser thirdUser = thirdUserService.lambdaQuery()
                .eq(ThirdUser::getChannelCode, payType)
                .eq(ThirdUser::getUserId, userAware.getUserId())
                .one();
        Assert.notNull(thirdUser);
        MiniAppOrderCreateReq createReq = new MiniAppOrderCreateReq();
        RequestHead head = new RequestHead(payConf.getChannelMchId(), new Date());
        createReq.setHead(head);
        MiniAppOrderCreateReq.Body body = new MiniAppOrderCreateReq.Body();
        body.setAppId(param.getAppId());
        body.setOrderNo(channelPaymentOrder.getPaymentOrderNo() + "");
        body.setBuyerId(thirdUser.getOpenId());
        body.setClientIp(userAware.getHostIp());
        body.setSubject(channelPaymentOrder.getSubject());
        body.setTotalAmount(channelPaymentOrder.getTotalAmount());
        body.setNotifyUrl(gatewayPaymentWenchuangNotify);
        body.setTxnTimeOut(mallTrade.getExpireTime().getTime() + "");
        body.setTenementId(userAware.getMchId() + "");
        body.setBody(channelPaymentOrder.getBody());
        if (StrUtil.isNotEmpty(appBaseConf.getAppAuthCode())) {
            JSONObject extendObj = new JSONObject();
            extendObj.putOpt("appAuthToken", appBaseConf.getAppAuthCode());
            body.setExtend(JSONUtil.toJsonStr(extendObj));
        }
        createReq.setBody(body);
        String sign = createReq.buildSign(payConf.getSalt());
        head.setSign(sign);
        ChannelResult<MiniAppOrderCreateResp> payResult = PaymentGatewaySender.createOrder(createReq, "http://apipayment.zhongzhiyou.cn");
        if (Objects.nonNull(payResult) && payResult.isSuccessful()) {
            MiniAppOrderCreateResp data = payResult.getData();
            if (Objects.nonNull(data)) {
                Long orderId = channelPaymentOrder.getId();
                JSONObject payParams = data.getPayParams();
                boolean update = mallTradeService.lambdaUpdate()
                        .eq(MallTrade::getTradeNo, mallTrade.getTradeNo())
                        .set(MallTrade::getPayChannel, mallTrade.getPayChannel())
                        .set(MallTrade::getMchOrderNo, channelPaymentOrder.getPaymentOrderNo())
                        .update();

                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(true);
                payParamVO.setPayParams(data.getPayParams());
                return SingleResponse.of(payParamVO);
            }
        } else {
            String code = payResult.getCode();
            String msg = payResult.getMsg();
            return SingleResponse.buildFailure(code, msg);
        }
        return SingleResponse.buildFailure("6985", "支付失败");
    }

    private SingleResponse<?> doDouYinPayment(OrderPaymentParam param, UserAware userAware, MallTrade mallTrade, MerchantAppPayConf payConf, MallPaymentOrder channelPaymentOrder) {
        CreateOrderReq req = new CreateOrderReq();
        DouYinPayConfig config = new DouYinPayConfig();
        config.setSalt(payConf.getSalt());
        req.setConfig(config);
        req.setAppId(payConf.getOutAppId());
        req.setBody(mallTrade.getProductName());
        req.setSubject(mallTrade.getProductName());
        req.setMsgPage(param.getFrontUrl());
        req.setTotalAmount(new BigDecimal(mallTrade.getTotalAmount()).longValue());
        req.setOutOrderNo(channelPaymentOrder.getPaymentOrderNo() + "");
        req.setValidTime(30 * 60);
        req.setNotifyUrl(douyinWenChuangPaymentNotifyUrl);
        ChannelResult<CreateOrderResp> channelResult = DouYinSender.createOrder(req);
        if (channelResult.isSuccessful()) {
            CreateOrderResp data = channelResult.getData();
            if (Objects.nonNull(data)) {
                Long orderId = channelPaymentOrder.getId();
                CreateOrderResp.Data payData = data.getData();
                boolean update = mallTradeService.lambdaUpdate()
                        .eq(MallTrade::getTradeNo, mallTrade.getTradeNo())
                        .set(MallTrade::getPayChannel, mallTrade.getPayChannel())
                        .set(MallTrade::getMchOrderNo, channelPaymentOrder.getPaymentOrderNo())
                        .update();

                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(true);
                payParamVO.setOrderId(payData.getOrder_id());
                payParamVO.setOrderToken(payData.getOrder_token());
                return SingleResponse.of(payParamVO);
            }
        } else {
            String code = channelResult.getCode();
            String msg = channelResult.getMsg();
            return SingleResponse.buildFailure(code, msg);
        }
        return null;
    }

}
