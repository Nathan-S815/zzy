package com.nuwa.app.ticket.command;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.nuwa.app.ticket.extpt.SupplierPlaceOrderExtPt;
import com.nuwa.client.ticket.dto.clientobject.order.PlaceOrderCmd;
import com.nuwa.client.ticket.dto.clientobject.order.TouristDTO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.manager.AlipayDataManager;
import com.nuwa.infrastructure.ticket.database.manager.TradeTransactionManager;
import com.nuwa.infrastructure.ticket.database.manager.dto.PlaceOrderDTO;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.UserTicketOrderJoinMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.UserTicketOrderListJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.service.TicketOrderService;
import com.nuwa.infrastructure.ticket.database.order.service.TouristInfoService;
import com.nuwa.infrastructure.ticket.database.order.vo.UserTicketOrderListVO;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.infrastructure.ticket.util.StopWatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * C端用户下单
 *
 * @author hy
 */
@Slf4j
@Component
public class PlaceOrderCmdExe implements ICommandExc<PlaceOrderCmd, SingleResponse<?>> {

    @Autowired
    private TradeTransactionManager tradeManager;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private TouristInfoService touristInfoService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private ScenicspotProductBookRuleConfigService scenicspotProductBookRuleConfigService;

    @Autowired
    private UserTicketOrderJoinMapper userTicketOrderJoinMapper;

    @Autowired
    private AlipayDataManager alipayDataManager;

    @Override
    public SingleResponse<?> execute(PlaceOrderCmd cmd) {
        log.info("cmd:{}", cmd);
        StopWatch stopWatch = new StopWatch("placeOrder");
        PlaceOrderDTO dto = new PlaceOrderDTO();
        BeanUtils.copyProperties(cmd, dto);

        MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(cmd.getLocalAppId());
        if (Objects.isNull(merchantAppBaseConf)) {
            return SingleResponse.buildFailure("9001", "下单失败,应用不存在");
        }
        dto.setSrcAppId(merchantAppBaseConf.getId());
        dto.setSrcAppName(merchantAppBaseConf.getAppName());
        dto.setClientSrc(merchantAppBaseConf.getAppType());
        ProductPriceEveryday productPriceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, cmd.getProductId())
                .eq(ProductPriceEveryday::getStatus, 0)
                .eq(ProductPriceEveryday::getDate, DateUtil.format(cmd.getVisitDate(),"yyyy-MM-dd"))
                .one();
        if (Objects.isNull(productPriceEveryday)) {
            return SingleResponse.buildFailure("9001", "下单失败,价格配置有误.");
        }

        if (productPriceEveryday.getStockNumber() != -1) {
            if (productPriceEveryday.getStockNumber() <= 0) {
                return SingleResponse.buildFailure("9001", "下单失败,库存为0");
            } else {
                if (cmd.getQuantity() > productPriceEveryday.getStockNumber()) {
                    return SingleResponse.buildFailure("9001", "下单失败,库存不足");
                }
            }
        }

        Integer stockModel = productPriceEveryday.getStockModel();
        if (stockModel.equals(1)) {
            if (Objects.isNull(cmd.getSessionId())) {
                return SingleResponse.buildFailure("9001", "下单失败,场次Id不能为空.");
            }
            ProductDayTime productDayTime = productDayTimeService.getById(cmd.getSessionId());
            if (Objects.isNull(productDayTime) || (productDayTime.getStockNumber() <= 0 && productDayTime.getStockNumber() != -1)) {
                return SingleResponse.buildFailure("9001", "下单失败,场次库存为0");
            } else {
                if (cmd.getQuantity() > productDayTime.getStockNumber()) {
                    return SingleResponse.buildFailure("9001", "下单失败,库存不足");
                }
            }
        }
        dto.setSessionId(cmd.getSessionId());

        Date current = DateUtil.beginOfDay(new Date());
        Integer beforeDay = productPriceEveryday.getBeforeDay();
        DateTime latestBookDay = DateUtil.offsetDay(cmd.getVisitDate(), -beforeDay);
        if (DateUtil.compare(current, latestBookDay) > 0) {
            return SingleResponse.buildFailure("9001", "下单失败,当前产品必须提前" + beforeDay + "天购买");
        }

        dto.setPriceEveryday(productPriceEveryday);
        ScenicspotProduct product = scenicspotProductService.getById(cmd.getProductId());
        Assert.notNull(product, "product can not be null");
        dto.setProduct(product);

        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(product.getSupplierId());
        Assert.notNull(supplierConfig, "supplierConfig can not be null");
        dto.setSupplierConfig(supplierConfig);

        stopWatch.start("checkRule");
        UserAware userAware = cmd.getUserAware();
        ScenicspotProductBookRuleConfig scenicspotProductBookRuleConfig = scenicspotProductBookRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductBookRuleConfig::getScenicspotProductId, product.getId())
                .eq(ScenicspotProductBookRuleConfig::getVersion, product.getVersion())
                .orderByDesc(ScenicspotProductBookRuleConfig::getVersion)
                .last("limit 1").one();
        if (!scenicspotProductBookRuleConfig.getMaxNumberByMobile().equals(-1)) {
            Boolean checkRet = checkMobileLimitRule(dto.getTouristList(), cmd.getVisitDate(), userAware.getMchId(), cmd.getProductId(), scenicspotProductBookRuleConfig.getLimitDayByMobile(), scenicspotProductBookRuleConfig.getMaxNumberByMobile());
            if (!checkRet) {
                return SingleResponse.buildFailure("9821", "手机号已超过最大预定限制");
            }
        }

        if (!scenicspotProductBookRuleConfig.getMaxNumberByCardId().equals(-1)) {
            Boolean checkRet = checkIdCardLimitRule(dto.getTouristList(), cmd.getVisitDate(), userAware.getMchId(), cmd.getProductId(), scenicspotProductBookRuleConfig.getLimitDayByCardId(), scenicspotProductBookRuleConfig.getMaxNumberByCardId());
            if (!checkRet) {
                return SingleResponse.buildFailure("9821", "身份证已超过最大预定限制");
            }
        }
        stopWatch.stop();

        log.info(">>>> placeOrder");
        stopWatch.start("tradeManager.placeOrder");
        dto.setUserAware(userAware);
        Long orderId = tradeManager.placeOrder(dto);
        stopWatch.stop();
        log.info("<<<< placeOrder[id:{}] success", orderId);

        log.info(">>>> alipayDataManager.orderUpdate");
        stopWatch.start("alipayDataManager.orderUpdate");
        dto.setUserAware(userAware);
        alipayDataManager.orderUpdate(orderId);
        stopWatch.stop();
        log.info("<<<< alipayDataManager.orderUpdate[id:{}] success", orderId);

        log.info(">>>> SupplierPlaceOrder[orderId:{}]", orderId);
        stopWatch.start("SupplierPlaceOrder");
        SingleResponse<?> supplierPlaceOrderResp = extensionExecutor.execute(SupplierPlaceOrderExtPt.class, cmd.getBizScenario(), ex -> ex.placeOrder(orderId));
        stopWatch.stop();
        log.info("<<<< SupplierPlaceOrder[orderId:{}] supplierPlaceOrderResp:{}", orderId, supplierPlaceOrderResp);

        log.info("placeOrder cost watch: {}", StopWatchUtil.prettyPrint(stopWatch));
        if (!supplierPlaceOrderResp.isSuccess()) {
            return supplierPlaceOrderResp;
        }
        return SingleResponse.of(orderId);
    }

    private Boolean checkMobileLimitRule(List<TouristDTO> touristList, Date visitDate, Long mchId, Long productId, Integer limitDay, Integer max) {
        Optional<TouristDTO> checkMobileBookRule = touristList.stream().filter(x -> {
            String mobile = x.getMobile();
            if (!StrUtil.isNullOrUndefined(mobile)) {
                List<Integer> statusIn = new ArrayList<>();
                statusIn.add(TicketOrderEnum.paying.getCode());
                statusIn.add(TicketOrderEnum.ticketing.getCode());
                statusIn.add(TicketOrderEnum.ticketed.getCode());
                UserTicketOrderListJoinQuery lambdaQuery = new UserTicketOrderListJoinQuery();
                lambdaQuery.setMchId(mchId);
                lambdaQuery.setProductId(productId);
                lambdaQuery.setMobile(mobile);
                lambdaQuery.setStatusIn(statusIn);
                lambdaQuery.setVisitDate(DateUtil.beginOfDay(DateUtil.offsetDay(visitDate, -(limitDay - 1))));
                List<UserTicketOrderListVO> userTicketOrderList = userTicketOrderJoinMapper.listAllByQuery(lambdaQuery);
                int sum = userTicketOrderList.stream().mapToInt(UserTicketOrderListVO::getQuantity).sum();
                if (!userTicketOrderList.isEmpty() && sum >= max) {
                    log.warn("mobile[{}]超过最大限制{}", mobile, max);
                    return true;
                }
            }
            return false;
        }).findAny();
        return !checkMobileBookRule.isPresent();
    }

    private Boolean checkIdCardLimitRule(List<TouristDTO> touristList, Date visitDate, Long mchId, Long productId, Integer limitDay, Integer max) {
        Optional<TouristDTO> checkMobileBookRule = touristList.stream().filter(x -> {
            String idCard = x.getIdCard();
            if (!StrUtil.isNullOrUndefined(idCard)) {
                UserTicketOrderListJoinQuery lambdaQuery = new UserTicketOrderListJoinQuery();
                lambdaQuery.setMchId(mchId);
                lambdaQuery.setIdCard(idCard);
                lambdaQuery.setProductId(productId);
                List<Integer> statusIn = new ArrayList<>();
                statusIn.add(TicketOrderEnum.paying.getCode());
                statusIn.add(TicketOrderEnum.ticketing.getCode());
                statusIn.add(TicketOrderEnum.ticketed.getCode());
                lambdaQuery.setStatusIn(statusIn);
                lambdaQuery.setVisitDate(DateUtil.beginOfDay(DateUtil.offsetDay(visitDate, -(limitDay - 1))));
                List<UserTicketOrderListVO> userTicketOrderList = userTicketOrderJoinMapper.listAllByQuery(lambdaQuery);
                int sum = userTicketOrderList.stream().mapToInt(UserTicketOrderListVO::getQuantity).sum();
                if (!userTicketOrderList.isEmpty() && sum >= max) {
                    log.warn("idCard[{}]超过最大限制{}", idCard, max);
                    return true;
                }
            }
            return false;
        }).findAny();
        return !checkMobileBookRule.isPresent();
    }
}
