package com.nuwa.ticket.start.api.controller.order;

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
import com.nuwa.client.ticket.api.order.OrderClientI;
import com.nuwa.client.ticket.api.order.param.ChannelPaymentSuccessNotifyParam;
import com.nuwa.client.ticket.dto.clientobject.order.PlaceOrderCmd;
import com.nuwa.client.ticket.dto.clientobject.order.qry.UserTicketOrderPageQry;
import com.nuwa.client.ticket.dto.domainevent.order.SupplierApplyRefundEvent;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.ticket.database.manager.AlipayDataManager;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
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
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.service.log.LogBizService;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinPayConfig;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.DouYinSender;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.req.CreateOrderReq;
import com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp.CreateOrderResp;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.H5OrderCreateReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.MiniAppOrderCreateReq;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.H5OrderCreateResp;
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
import com.nuwa.infrastructure.ticket.lock.DistributedLocker;
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
@RequestMapping("order")
@Api(tags = {"订单相关"})
public class OrderController {

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

    @Value("${douyin.payment.notify.url}")
    private String douyinPaymentNotifyUrl;

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

    @Autowired
    private OrderClientI orderClientI;

    @Autowired
    private AlipayDataManager alipayDataManager;

    @ApiOperation(value = "下单")
    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> placeOrder(@RequestBody @Valid PlaceOrderCmd cmd, UserAware userAware) {
        ScenicspotProduct product = scenicspotProductService.getById(cmd.getProductId());
        Assert.notNull(product, "产品不存在");
        Assert.isTrue(product.getStatus().equals(1), "未上架的产品不允许购买");
        if (Objects.isNull(cmd.getLocalAppId())) {
            cmd.setLocalAppId(30L);
        }

        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(product.getSupplierId());
        Assert.notNull(supplierConfig, "供应商未配置");

        if (Objects.nonNull(cmd.getTouristList())) {
            boolean present = cmd.getTouristList().stream().anyMatch(x -> StrUtil.isNotBlank(x.getIdCard()) && !IdcardUtil.isValidCard(x.getIdCard()));
            if (present) {
                return SingleResponse.buildFailure("9001", "游玩人身份证号格式错误");
            }
        }
        BizScenario bizScenario = BizScenario.valueOf(ExtensionConstant.BIZ_ID_SUPPLIER, ExtensionConstant.USE_CASE_PLACE_ORDER, supplierConfig.getSupplierId() + "");
        cmd.setBizScenario(bizScenario);
        if (StrUtil.isNullOrUndefined(cmd.getPromoterUserId())) {
            String shareCode = stringRedisTemplate.opsForValue().get(RedisKeyConstant.REDIS_KEY_SHARE_USER + ":" + userAware.getUserId());
            if (!StrUtil.isNullOrUndefined(shareCode)) {
                cmd.setPromoterUserId(shareCode);
            }
        }
        return placeOrderCmdExe.execute(cmd);
    }

    @ApiOperation(value = "支付")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> payment(@RequestBody @Valid OrderPaymentParam param, UserAware userAware) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TicketOrder ticketOrder = ticketOrderService.getById(param.getOrderId());
        Assert.notNull(ticketOrder, "订单不存在");
        Assert.isTrue(ticketOrder.getStatus().equals(0) || ticketOrder.getStatus().equals(1), "当前状态不允许支付");

        if (ticketOrder.getTimeExpire().getTime() < (new Date()).getTime()) {
            return SingleResponse.buildFailure("9874", "订单已超过支付时间,不允许支付");
        }
        MerchantAppPayConf payConf;
        payConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getOutAppId, param.getAppId()).last("limit 1").one();
        Assert.isTrue(Objects.nonNull(payConf), "支付配置错误");

        ChannelCreateOrderDTO payChannelPlaceOrderDto = new ChannelCreateOrderDTO();
        payChannelPlaceOrderDto.setPayType(param.getPayType());
        payChannelPlaceOrderDto.setUserAware(userAware);
        payChannelPlaceOrderDto.setTicketOrder(ticketOrder);
        payChannelPlaceOrderDto.setPayConf(payConf);
        ChannelPaymentOrder channelPaymentOrder = userTradeManager.channelCreateOrder(payChannelPlaceOrderDto);

        if (Objects.nonNull(channelPaymentOrder)) {
            if (ticketOrder.getAmount().compareTo(new BigDecimal("0")) == 0) {
                return doZeroPayment(param, userAware, ticketOrder, payConf, channelPaymentOrder);
            }

            if ("douyin_mini".equals(param.getPayType())) {
                SingleResponse<?> payParamVO = doDouYinPayment(param, userAware, ticketOrder, payConf, channelPaymentOrder);
                if (payParamVO != null) {
                    return payParamVO;
                }
            } else if ("weixin_mini".equals(param.getPayType()) || "weixin_mp".equalsIgnoreCase(param.getPayType())) {
                SingleResponse<?> payParamVO = doGatewayMiniPay(param, userAware, ticketOrder, payConf, channelPaymentOrder);
                if (payParamVO != null) {
                    return payParamVO;
                }
            } else if ("alipay_mini".equals(param.getPayType())) {
                log.info(">>>> alipay_mini");
                SingleResponse<?> payParamVO = doGatewayMiniPay(param, userAware, ticketOrder, payConf, channelPaymentOrder);
                if (payParamVO != null) {
                    alipayDataManager.orderUpdate(ticketOrder.getId());
                    return payParamVO;
                }
            } else if ("alipay_mini_template".equals(param.getPayType())) {
                log.info(">>>> alipay_mini");
                SingleResponse<?> payParamVO = doGatewayMiniPay(param, userAware, ticketOrder, payConf, channelPaymentOrder);
                if (payParamVO != null) {
                    alipayDataManager.orderUpdate(ticketOrder.getId());
                    return payParamVO;
                }
            } else if ("alipay_h5".equals(param.getPayType())) {
                log.info(">>>> alipay_h5");
                SingleResponse<?> payParamVO = doGatewayH5Pay(param, userAware, ticketOrder, payConf, channelPaymentOrder);
                if (payParamVO != null) {
                    alipayDataManager.orderUpdate(ticketOrder.getId());
                    return payParamVO;
                }
            }
        }
        stopWatch.stop();
        log.info("cost time:{} ms", stopWatch.getTotalTimeMillis());
        return SingleResponse.buildFailure("9863", "支付失败");
    }

    @ApiOperation(value = "取消")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> cancel(@RequestBody @Valid OrderCancelParam param, UserAware userAware) {
        return doCancelOrder(param.getOrderId(), userAware);
    }

    private SingleResponse<?> doCancelOrder(Long orderId, UserAware userAware) {
        log.info(">>>> TicketOrder orderId [{}]", orderId);
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        Assert.notNull(ticketOrder, "订单不存在");
        Integer status = ticketOrder.getStatus();
        Assert.notNull(ticketOrder.getUserId().equals(userAware.getUserId()), "当前订单所属用户不对,不允许操作");
        Assert.isTrue(status.equals(TicketOrderEnum.created.getCode()) || status.equals(TicketOrderEnum.paying.getCode()),
                "当前订单状态不允许取消操作");
        boolean update = ticketOrderService.lambdaUpdate()
                .set(TicketOrder::getStatus, TicketOrderEnum.closed.getCode())
                .set(TicketOrder::getLastUpdateTime, new Date())
                .eq(TicketOrder::getId, ticketOrder.getId())
                .update();
        if (update) {
            log.info("cancel TicketOrder[id:{}] success.", ticketOrder.getId());

            ProductPriceEveryday productPriceEveryday = productPriceEverydayService.lambdaQuery()
                    .eq(ProductPriceEveryday::getScenicspotProductId, ticketOrder.getProductId())
                    .eq(ProductPriceEveryday::getStatus, 0)
                    .eq(ProductPriceEveryday::getDate, ticketOrder.getVisitDate())
                    .one();
            if (Objects.nonNull(productPriceEveryday)) {
                log.info(">>>> decrease Stock");
                if (!productPriceEveryday.getStockNumber().equals(-1L)) {
                    boolean updateStock = productPriceEverydayService
                            .incrementUpdate(ProductPriceEveryday::getStockNumber, ticketOrder.getQuantity())
                            .eq(ProductPriceEveryday::getId, productPriceEveryday.getId())
                            .update();
                    Assert.isTrue(updateStock);
                    log.info("increment Stock success.");

                    if (productPriceEveryday.getStockModel().equals(1) && Objects.nonNull(ticketOrder.getSessionId())) {
                        boolean updateSessionStock = productDayTimeService
                                .incrementUpdate(ProductDayTime::getStockNumber, ticketOrder.getQuantity())
                                .eq(ProductDayTime::getId, ticketOrder.getSessionId())
                                .update();
                        Assert.isTrue(updateSessionStock);
                        log.info("increment Session Stock success.");
                    }
                }
            }
            alipayDataManager.orderUpdate(ticketOrder.getId());
        }

        log.info("<<<< TicketOrder orderId [{}] success", orderId);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取退款规则")
    @RequestMapping(value = "/refund/getRule", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserRefundConfirmVO> getOrderRefundRule(@Valid GetOrderRefundRuleParam param, UserAware userAware) {
        log.info(">>>> TicketOrder refundConfirm [{}]", param);
        UserRefundConfirmVO vo = new UserRefundConfirmVO();
        TicketOrder ticketOrder = ticketOrderService.getById(param.getOrderId());
        ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(ticketOrder.getProductId());
        ScenicspotProductRefundRuleConfig lastRefundRuleConfig = getScenicspotProductRefundRuleConfig(scenicspotProduct.getId(), ticketOrder.getSnapshootVersion().longValue());
        Assert.notNull(lastRefundRuleConfig);
        ProductRefundRuleConfigDTO refundRuleConfigDTO = new ProductRefundRuleConfigDTO();
        BeanUtils.copyProperties(lastRefundRuleConfig, refundRuleConfigDTO);

        vo.setOrderId(ticketOrder.getId());
        BigDecimal refundingAmount = ticketOrder.getUnitPrice().multiply(new BigDecimal(ticketOrder.getRefundingQuantity()));
        vo.setAvailableRefundAmount(ticketOrder.getRealAmount().subtract(refundingAmount).subtract(ticketOrder.getRefundedAmount()));
        vo.setAvailableRefundQuantity(ticketOrder.getQuantity() - ticketOrder.getRefundedQuantity() - ticketOrder.getRefundingQuantity());
        vo.setRefundRuleConfig(refundRuleConfigDTO);
        vo.setProductType(scenicspotProduct.getTicketKind());
        List<String> reasonList = new ArrayList<>();
        reasonList.add("行程取消/变更");
        reasonList.add("门票预定错误");
        reasonList.add("价格不优惠");
        reasonList.add("入园问题");
        reasonList.add("其他");
        vo.setReasonList(reasonList);
        log.info("<<<< TicketOrder[{}] refundConfirm success.", param);
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "退款")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> refund(@RequestBody @Valid OrderRefundParam param, UserAware userAware) {
        log.info(">>>> TicketOrder refund [{}]", param);
        TicketOrder ticketOrder = ticketOrderService.getById(param.getOrderId());
        if (param.getQuantity() == null || param.getQuantity() <= 0) {
            return SingleResponse.buildFailure("9847", "可退票数不足");
        }
        ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(ticketOrder.getProductId());
        ScenicspotProductRefundRuleConfig lastRefundRuleConfig = getScenicspotProductRefundRuleConfig(scenicspotProduct.getId(), ticketOrder.getSnapshootVersion().longValue());
        if (Objects.isNull(lastRefundRuleConfig) || lastRefundRuleConfig.getRefundMode().equals(1)) {
            return SingleResponse.buildFailure("9847", "当前订单不允许退款");
        }
        Assert.notNull(ticketOrder, "订单不存在");
        Integer status = ticketOrder.getStatus();
        Assert.notNull(ticketOrder.getUserId().equals(userAware.getUserId()), "当前订单所属用户不对,不允许操作");
        Assert.isTrue(status.equals(TicketOrderEnum.ticketed.getCode()), "当前订单状态不允许退款操作");
        TicketRefund ticketRefund = null;
        String lockKey = "apply:refund:" + param.getOrderId();
        try {
            boolean getLock = distributedLocker.tryLock(lockKey, TimeUnit.SECONDS, 3, 2);
            if (!getLock) {
                return SingleResponse.buildFailure("9847", "获取锁失败");
            }
            TicketOrder newTicketOrder = ticketOrderService.getById(param.getOrderId());
            int alreadyRefundCount = newTicketOrder.getRefundingQuantity() + newTicketOrder.getRefundedQuantity() + newTicketOrder.getAlreadyConsumeQuantity();
            if (alreadyRefundCount + param.getQuantity() > ticketOrder.getQuantity()) {
                return SingleResponse.buildFailure("9847", "可退票数不足");
            }

            UserApplyRefundDTO dto = new UserApplyRefundDTO();
            dto.setOrderId(param.getOrderId());
            dto.setQuantity(param.getQuantity());
            dto.setUserAware(userAware);
            dto.setReason(param.getReason());
            RefundApplyRet refundApplyRet = userTradeManager.refundApply(dto);

            ticketRefund = refundApplyRet.getTicketRefund();
            SupplierRefundOrder supplierRefundOrder = refundApplyRet.getSupplierRefundOrder();
            SupplierApplyRefundEvent event = new SupplierApplyRefundEvent();
            event.setTicketRefundId(ticketRefund.getId());
            event.setSupplierRefundOrderId(supplierRefundOrder.getId());
            domainEventPublisher.publishEvent(event);
            log.info("publishEvent UserApplyRefundSuccessEvent [{}]  success.", event);

            alipayDataManager.orderUpdate(ticketOrder.getId());

        } catch (Exception ex) {
            log.error("获取锁失败,key:{}", lockKey);
        } finally {
            distributedLocker.unlock(lockKey);
        }

        log.info("<<<< TicketOrder[{}] refund success.", param);
        if (Objects.nonNull(ticketRefund)) {
            return SingleResponse.of(ticketRefund.getId());
        } else {
            return SingleResponse.buildFailure("9875", "退款申请失败");
        }
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<UserOrderPageVO>> page(@Valid UserTicketOrderPageQry pageQry, UserAware userAware) {
        UserTicketOrderPageParam pageParam = new UserTicketOrderPageParam(pageQry);
        IPage<UserOrderPageVO> pageData = ticketOrderService.paginateAndConvert(pageParam, UserOrderPageVO::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取订单详情")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserOrderDetailVO> getDetail(@PathVariable Long id, UserAware userAware) {
        TicketOrder ticketOrder = ticketOrderService.getById(id);
        Assert.isTrue(ticketOrder.getUserId().equals(userAware.getUserId()), "订单不存在");
        ScenicspotProduct product = scenicspotProductService.getById(ticketOrder.getProductId());
        Scenicspot scenicspot = scenicspotService.getById(product.getScenicspotId());

        UserOrderDetailVO vo = UserOrderDetailVO.toVO(ticketOrder);
        vo.setServicePhone(product.getServicePhone());
        List<TouristInfo> touristInfoList = touristInfoService.lambdaQuery().eq(TouristInfo::getOrderId, id).list();
        List<TouristDTO> touristDTOList = touristInfoList.stream().map(x -> {
            TouristDTO dto = new TouristDTO();
            BeanUtils.copyProperties(x, dto);
            return dto;
        }).collect(Collectors.toList());
        vo.setTouristList(touristDTOList);

        ProductVerificationRuleConfigDTO verificationRule = new ProductVerificationRuleConfigDTO();
        ScenicspotProductVerificationConfig lastVerificationConfig = scenicspotProductVerificationConfigService.lambdaQuery()
                .eq(ScenicspotProductVerificationConfig::getScenicspotProductId, ticketOrder.getProductId())
                .eq(ScenicspotProductVerificationConfig::getVersion, ticketOrder.getSnapshootVersion())
                .orderByDesc(ScenicspotProductVerificationConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastVerificationConfig, verificationRule);

        //qrCode|verificationCode|mobile|idCard
        String[] splitEntranceCertificate = verificationRule.getEntranceCertificate().split("\\|");
        List<EntranceCertificateVO> certificateVoItems = Arrays.stream(splitEntranceCertificate).map(x -> {
            EntranceCertificateVO certificateVO = new EntranceCertificateVO();
            certificateVO.setCode(x);
            if ("qrCode".equalsIgnoreCase(x)) {
                certificateVO.setValue("二维码");
            } else if ("verificationCode".equalsIgnoreCase(x)) {
                certificateVO.setValue("核销码");
            } else if ("mobile".equalsIgnoreCase(x)) {
                certificateVO.setValue("手机号");
            } else if ("idCard".equalsIgnoreCase(x)) {
                certificateVO.setValue("身份证");
            } else if ("sms".equalsIgnoreCase(x)) {
                certificateVO.setValue("商家短信");
            } else {
                certificateVO.setValue("");
            }
            return certificateVO;
        }).collect(Collectors.toList());
        verificationRule.setEntranceCertificateItems(certificateVoItems);
        vo.setVerificationRule(verificationRule);

        ProductRefundRuleConfigDTO refundRule = new ProductRefundRuleConfigDTO();
        ScenicspotProductRefundRuleConfig lastRefundRuleConfig = scenicspotProductRefundRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductRefundRuleConfig::getScenicspotProductId, ticketOrder.getProductId())
                .eq(ScenicspotProductRefundRuleConfig::getVersion, ticketOrder.getSnapshootVersion())
                .orderByDesc(ScenicspotProductRefundRuleConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastRefundRuleConfig, refundRule);
        vo.setRefundRule(refundRule);
        vo.setCanRefund(vo.getCanRefund() && !refundRule.getRefundMode().equals(1));
        vo.setOpenTime(scenicspot.getOpenTime());

        if (Objects.nonNull(ticketOrder.getSessionId())) {
            ProductDayTime productDayTime = productDayTimeService.getById(ticketOrder.getSessionId());
            if (Objects.nonNull(productDayTime)) {
                if (Objects.nonNull(productDayTime.getStart())) {
                    vo.setSessionStartTime(DateUtil.format(productDayTime.getStart(), "HH:mm"));
                } else {
                    vo.setSessionStartTime("00:00");
                }
                if (Objects.nonNull(productDayTime.getEnd())) {
                    vo.setSessionEndTime(DateUtil.format(productDayTime.getEnd(), "HH:mm"));
                } else {
                    vo.setSessionEndTime("00:00");
                }
            }
        }

        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "根据订单id获取出票结果(单张)")
    @Deprecated
    @RequestMapping(value = "/{id}/ticketResult", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OrderVoucher> getTicketResult(@PathVariable Long id, UserAware userAware) {
        TicketOrder ticketOrder = ticketOrderService.getById(id);
        Assert.isTrue(ticketOrder.getUserId().equals(userAware.getUserId()), "订单不存在");
        OrderVoucher orderVoucher = orderVoucherService.lambdaQuery().eq(OrderVoucher::getOrderId, id).last("limit 1").one();
        if (Objects.nonNull(orderVoucher)) {
            return SingleResponse.of(orderVoucher);
        }
        return SingleResponse.buildFailure("9865", "未获取到出票结果");
    }

    @ApiOperation(value = "根据订单id获取出票结果(多张核销码) （新应用都调用这个接口）")
    @RequestMapping(value = "/{id}/listTicket", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<OrderVoucher>> listTicket(@PathVariable Long id, UserAware userAware) {
        TicketOrder ticketOrder = ticketOrderService.getById(id);
        Assert.isTrue(ticketOrder.getUserId().equals(userAware.getUserId()), "订单不存在");
        List<OrderVoucher> list = orderVoucherService.lambdaQuery().eq(OrderVoucher::getOrderId, id).list();
        if (Objects.nonNull(list)) {
            return SingleResponse.of(list);
        }
        return SingleResponse.buildFailure("9865", "未获取到出票结果");
    }

    @ApiOperation(value = "退款订单分页查询")
    @RequestMapping(value = "refund/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserRefundOrderPageVO>> refundPage(UserRefundOrderPageJoinQry query, UserAware userAware) {
        UserRefundPageJoinQuery queryPage = new UserRefundPageJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setUserId(userAware.getUserId());
        Page<UserRefundOrderPageVO> pageData = userRefundOrderJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            Integer status = x.getStatus();
            if (status.equals(1) || status.equals(2)) {
                x.setStatusName("退款中");
            } else if (status.equals(3)) {
                x.setStatusName("退款成功");
            } else if (status.equals(4)) {
                x.setStatusName("退款失败");
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "退款详情")
    @RequestMapping(value = "refund/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserRefundDetailVO> refundDetail(@PathVariable("id") Long id, UserAware userAware) {
        UserRefundDetailVO detailVO = new UserRefundDetailVO();
        TicketRefund ticketRefund = ticketRefundService.getById(id);
        Assert.isTrue(ticketRefund.getUserId().equals(userAware.getUserId()), "退款订单不存在");
        Long orderId = ticketRefund.getOrderId();
        TicketOrder ticketOrder = ticketOrderService.getById(orderId);
        BeanUtils.copyProperties(ticketOrder, detailVO);
        detailVO.setRefundApplyTime(ticketRefund.getCreateTime());
        detailVO.setFailureMsg(ticketRefund.getFailureMsg());
        detailVO.setRefundQuantity(ticketRefund.getQuantity());
        detailVO.setRefundAmount(ticketRefund.getAmount());
        detailVO.setRefundReason(ticketRefund.getRefundReason());
        detailVO.setRefundSerialNo(ticketRefund.getRefundSerialNo());
        detailVO.setScenicspotId(ticketOrder.getScenicspotId());
        detailVO.setProductId(ticketOrder.getProductId());
        Integer refundStatus = ticketRefund.getStatus();
        if (refundStatus.equals(1) || refundStatus.equals(2)) {
            detailVO.setRefundStatusName("退款中");
        } else if (refundStatus.equals(3)) {
            detailVO.setRefundStatusName("退款成功");
        } else if (refundStatus.equals(4)) {
            detailVO.setRefundStatusName("退款失败");
        }
        detailVO.setRefundStatus(ticketRefund.getStatus());
        ProductRefundRuleConfigDTO refundRule = new ProductRefundRuleConfigDTO();
        ScenicspotProductRefundRuleConfig lastRefundRuleConfig = scenicspotProductRefundRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductRefundRuleConfig::getScenicspotProductId, ticketOrder.getProductId())
                .eq(ScenicspotProductRefundRuleConfig::getVersion, ticketOrder.getSnapshootVersion())
                .orderByDesc(ScenicspotProductRefundRuleConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastRefundRuleConfig, refundRule);
        detailVO.setRefundRule(refundRule);

        List<TouristInfo> touristInfoList = touristInfoService.lambdaQuery().eq(TouristInfo::getOrderId, ticketOrder.getId()).list();
        List<TouristDTO> touristDTOList = touristInfoList.stream().map(x -> {
            TouristDTO dto = new TouristDTO();
            BeanUtils.copyProperties(x, dto);
            return dto;
        }).collect(Collectors.toList());
        detailVO.setTouristList(touristDTOList);
        detailVO.setRefundType(1);
        return SingleResponse.of(detailVO);
    }

    private ScenicspotProductValidPeriodConfig getScenicspotProductValidPeriodConfig(Long productId, Long version) {
        return scenicspotProductValidPeriodConfigService.lambdaQuery()
                .eq(ScenicspotProductValidPeriodConfig::getScenicspotProductId, productId)
                .eq(ScenicspotProductValidPeriodConfig::getVersion, version)
                .orderByDesc(ScenicspotProductValidPeriodConfig::getVersion)
                .last("limit 1").one();
    }

    private ScenicspotProductRefundRuleConfig getScenicspotProductRefundRuleConfig(Long productId, Long version) {
        return scenicspotProductRefundRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductRefundRuleConfig::getScenicspotProductId, productId)
                .eq(ScenicspotProductRefundRuleConfig::getVersion, version)
                .orderByDesc(ScenicspotProductRefundRuleConfig::getVersion)
                .last("limit 1").one();
    }

    private ScenicspotProductVerificationConfig getScenicspotProductVerificationConfig(Long productId, Long version) {
        return scenicspotProductVerificationConfigService.lambdaQuery()
                .eq(ScenicspotProductVerificationConfig::getScenicspotProductId, productId)
                .eq(ScenicspotProductVerificationConfig::getVersion, version)
                .orderByDesc(ScenicspotProductVerificationConfig::getVersion)
                .last("limit 1").one();
    }

    private ScenicspotProductBookRuleConfig getScenicspotProductBookRuleConfig(Long productId, Long version) {
        return scenicspotProductBookRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductBookRuleConfig::getScenicspotProductId, productId)
                .eq(ScenicspotProductBookRuleConfig::getVersion, version)
                .orderByDesc(ScenicspotProductBookRuleConfig::getVersion)
                .last("limit 1").one();
    }

    private SingleResponse<?> doDouYinPayment(OrderPaymentParam param, UserAware userAware, TicketOrder ticketOrder, MerchantAppPayConf payConf, ChannelPaymentOrder channelPaymentOrder) {
        CreateOrderReq req = new CreateOrderReq();
        DouYinPayConfig config = new DouYinPayConfig();
        config.setSalt(payConf.getSalt());
        req.setConfig(config);
        req.setAppId(payConf.getOutAppId());
        req.setBody(ticketOrder.getProductName());
        req.setSubject(ticketOrder.getProductName());
        req.setMsgPage(param.getFrontUrl());
        req.setTotalAmount(ticketOrder.getRealAmount().multiply(new BigDecimal("100")).longValue());
        req.setOutOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        req.setValidTime(30 * 60);
        req.setNotifyUrl(douyinPaymentNotifyUrl);
        ChannelResult<CreateOrderResp> channelResult = DouYinSender.createOrder(req);
        if (channelResult.isSuccessful()) {
            CreateOrderResp data = channelResult.getData();
            if (Objects.nonNull(data)) {
                Long orderId = channelPaymentOrder.getId();
                CreateOrderResp.Data payData = data.getData();

                ChannelCreateOrderSuccessDTO channelPlaceOrderSuccessDTO = new ChannelCreateOrderSuccessDTO();
                channelPlaceOrderSuccessDTO.setUserAware(userAware);
                channelPlaceOrderSuccessDTO.setTicketOrder(ticketOrder);
                channelPlaceOrderSuccessDTO.setChannelPaymentOrder(channelPaymentOrderService.getById(orderId));
                channelPlaceOrderSuccessDTO.setChannelOrderNo(payData.getOrder_id());
                userTradeManager.channelCreateOrderSuccess(channelPlaceOrderSuccessDTO);

                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(true);
                payParamVO.setOrderId(payData.getOrder_id());
                payParamVO.setOrderToken(payData.getOrder_token());
                return SingleResponse.of(payParamVO);
            }
        } else {
            String code = channelResult.getCode();
            String msg = channelResult.getMsg();

            boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                    .set(TicketOrder::getFailureCode, code)
                    .set(TicketOrder::getFailureMsg, msg)
                    .set(TicketOrder::getLastUpdateTime, new Date())
                    .eq(TicketOrder::getId, ticketOrder.getId())
                    .update();
            if (updateTicketOrder) {
                log.info("update TicketOrder[orderId:{}] FailureCode:{},FailureMsg:{}", ticketOrder.getId(), channelResult.getCode(), channelResult.getMsg());
            }

            return SingleResponse.buildFailure(code, msg);
        }
        return null;
    }

    private SingleResponse<?> doGatewayMiniPay(OrderPaymentParam param, UserAware userAware, TicketOrder ticketOrder, MerchantAppPayConf payConf, ChannelPaymentOrder channelPaymentOrder) {
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
        body.setOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        body.setBuyerId(thirdUser.getOpenId());
        body.setClientIp(userAware.getHostIp());
        body.setSubject(ticketOrder.getProductName());
        body.setTotalAmount(channelPaymentOrder.getAmount().multiply(new BigDecimal("100")).longValue());
        body.setNotifyUrl(gatewayPaymentNotify);
        body.setTxnTimeOut(ticketOrder.getTimeExpire().getTime() + "");
        body.setTenementId(userAware.getMchId() + "");
        body.setBody(ticketOrder.getProductName());
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
                ChannelCreateOrderSuccessDTO channelPlaceOrderSuccessDTO = new ChannelCreateOrderSuccessDTO();
                channelPlaceOrderSuccessDTO.setUserAware(userAware);
                channelPlaceOrderSuccessDTO.setTicketOrder(ticketOrder);
                channelPlaceOrderSuccessDTO.setChannelPaymentOrder(channelPaymentOrderService.getById(orderId));
                channelPlaceOrderSuccessDTO.setChannelOrderNo(data.getPlatOrderNo());
                if (Objects.nonNull(payParams)) {
                    channelPlaceOrderSuccessDTO.setExtJson(JSONUtil.toJsonStr(payParams));
                }
                userTradeManager.channelCreateOrderSuccess(channelPlaceOrderSuccessDTO);

                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(true);
                payParamVO.setPayParams(data.getPayParams());
                return SingleResponse.of(payParamVO);
            }
        } else {
            String code = payResult.getCode();
            String msg = payResult.getMsg();
            boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                    .set(TicketOrder::getFailureCode, code)
                    .set(TicketOrder::getFailureMsg, msg)
                    .set(TicketOrder::getLastUpdateTime, new Date())
                    .eq(TicketOrder::getId, ticketOrder.getId())
                    .update();
            if (updateTicketOrder) {
                log.info("update TicketOrder[orderId:{}] FailureCode:{},FailureMsg:{}", ticketOrder.getId(), payResult.getCode(), payResult.getMsg());
            }
            return SingleResponse.buildFailure(code, msg);
        }
        return null;
    }

    private SingleResponse<?> doGatewayH5Pay(OrderPaymentParam param, UserAware userAware, TicketOrder ticketOrder, MerchantAppPayConf payConf, ChannelPaymentOrder channelPaymentOrder) {
        Long mchClientId = payConf.getMchClientId();
        MerchantAppBaseConf appBaseConf = merchantAppBaseConfService.getById(mchClientId);
        String payType = param.getPayType();
        ThirdUser thirdUser = thirdUserService.lambdaQuery()
                .eq(ThirdUser::getChannelCode, payType)
                .eq(ThirdUser::getUserId, userAware.getUserId())
                .one();
        Assert.notNull(thirdUser);
        H5OrderCreateReq createReq = new H5OrderCreateReq();
        RequestHead head = new RequestHead(payConf.getChannelMchId(), new Date());
        createReq.setHead(head);
        H5OrderCreateReq.Body body = new H5OrderCreateReq.Body();
        body.setAppId(param.getAppId());
        body.setOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        body.setClientIp(userAware.getHostIp());
        body.setSubject(ticketOrder.getProductName());
        body.setTotalAmount(channelPaymentOrder.getAmount().multiply(new BigDecimal("100")).longValue());
        body.setNotifyUrl(gatewayPaymentNotify);
        body.setTxnTimeOut(ticketOrder.getTimeExpire().getTime() + "");
        body.setTenementId(userAware.getMchId() + "");
        body.setBody(ticketOrder.getProductName());
        if (StrUtil.isNotEmpty(appBaseConf.getAppAuthCode())) {
            JSONObject extendObj = new JSONObject();
            extendObj.putOpt("appAuthToken", appBaseConf.getAppAuthCode());
            body.setExtend(JSONUtil.toJsonStr(extendObj));
        }
        createReq.setBody(body);
        String sign = createReq.buildSign(payConf.getSalt());
        head.setSign(sign);
        ChannelResult<H5OrderCreateResp> payResult = PaymentGatewaySender.createH5Order(createReq, "http://apipayment.zhongzhiyou.cn");
        if (Objects.nonNull(payResult) && payResult.isSuccessful()) {
            H5OrderCreateResp data = payResult.getData();
            if (Objects.nonNull(data)) {
                Long orderId = channelPaymentOrder.getId();
                JSONObject payParams = data.getPayParams();
                ChannelCreateOrderSuccessDTO channelPlaceOrderSuccessDTO = new ChannelCreateOrderSuccessDTO();
                channelPlaceOrderSuccessDTO.setUserAware(userAware);
                channelPlaceOrderSuccessDTO.setTicketOrder(ticketOrder);
                channelPlaceOrderSuccessDTO.setChannelPaymentOrder(channelPaymentOrderService.getById(orderId));
                channelPlaceOrderSuccessDTO.setChannelOrderNo(data.getPlatOrderNo());
                if (Objects.nonNull(payParams)) {
                    channelPlaceOrderSuccessDTO.setExtJson(JSONUtil.toJsonStr(payParams));
                }
                userTradeManager.channelCreateOrderSuccess(channelPlaceOrderSuccessDTO);

                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(true);
                payParamVO.setPayParams(data.getPayParams());
                return SingleResponse.of(payParamVO);
            }
        } else {
            String code = payResult.getCode();
            String msg = payResult.getMsg();
            boolean updateTicketOrder = ticketOrderService.lambdaUpdate()
                    .set(TicketOrder::getFailureCode, code)
                    .set(TicketOrder::getFailureMsg, msg)
                    .set(TicketOrder::getLastUpdateTime, new Date())
                    .eq(TicketOrder::getId, ticketOrder.getId())
                    .update();
            if (updateTicketOrder) {
                log.info("update TicketOrder[orderId:{}] FailureCode:{},FailureMsg:{}", ticketOrder.getId(), payResult.getCode(), payResult.getMsg());
            }
            return SingleResponse.buildFailure(code, msg);
        }
        return null;
    }


    private SingleResponse<?> doZeroPayment(OrderPaymentParam param, UserAware userAware, TicketOrder ticketOrder, MerchantAppPayConf payConf, ChannelPaymentOrder channelPaymentOrder) {
        Long orderId = channelPaymentOrder.getId();

        ChannelCreateOrderSuccessDTO channelPlaceOrderSuccessDTO = new ChannelCreateOrderSuccessDTO();
        channelPlaceOrderSuccessDTO.setUserAware(userAware);
        channelPlaceOrderSuccessDTO.setTicketOrder(ticketOrder);
        channelPlaceOrderSuccessDTO.setChannelPaymentOrder(channelPaymentOrderService.getById(orderId));
        channelPlaceOrderSuccessDTO.setChannelOrderNo(RandomUtil.randomNumbers(10));
        userTradeManager.channelCreateOrderSuccess(channelPlaceOrderSuccessDTO);

        ChannelPaymentSuccessNotifyParam dto = new ChannelPaymentSuccessNotifyParam();
        dto.setOrderNo(channelPaymentOrder.getMchPayOrderNo() + "");
        try {
            SingleResponse<?> payResult = orderClientI.paymentSuccess(dto);
            if (payResult.isSuccess()) {
                DouYinPayParamVO payParamVO = new DouYinPayParamVO();
                payParamVO.setNeedPay(false);
                return SingleResponse.of(payParamVO);
            }
        } catch (Exception ex) {
            log.error("orderClientI.paymentSuccess invoke error", ex);
            doCancelOrder(ticketOrder.getId(), userAware);
        }
        return SingleResponse.buildFailure("9871", "支付失败");
    }

}
