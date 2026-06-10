package com.nuwa.ticket.start.api.controller.product;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.ticket.command.query.UserProductPageJoinQry;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.UserProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByUserProductQuery;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.product.vo.UserProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.MerchantCanSelectScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.ScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.mapper.UserScenicspotJoinMapper;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.*;
import com.nuwa.ticket.start.api.controller.dto.*;
import com.nuwa.ticket.start.api.controller.product.param.ListMonthPriceParam;
import com.nuwa.ticket.start.api.controller.product.vo.DayPriceVO;
import com.nuwa.ticket.start.api.controller.product.vo.DaySessionVO;
import com.nuwa.ticket.start.api.controller.product.vo.ProductRuleVO;
import com.nuwa.ticket.start.api.controller.product.vo.UserProductLabelPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("product")
@Api(tags = {"门票相关"})
public class ProductController {

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
    private UserProductJoinMapper userProductJoinMapper;

    @ApiOperation(value = "门票分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserProductLabelPageVO>> selectByPage(@Valid UserProductPageJoinQry query) {
        PageByUserProductQuery queryPage = new PageByUserProductQuery();
        Assert.isTrue(StrUtil.isNotBlank(query.getMchId()));
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setStatus(1);
        String mchId = query.getMchId();
        queryPage.setDistributeMerchantId(Long.parseLong(mchId));
        Page<UserProductPageVO> pageData = userProductJoinMapper.paginateByQuery(queryPage);
        Page<UserProductLabelPageVO> labelPageVO = new Page<>();
        BeanUtils.copyProperties(pageData, labelPageVO);
        List<UserProductLabelPageVO> labelItems = pageData.getRecords().stream().map(x -> {
            UserProductLabelPageVO vo = new UserProductLabelPageVO();
            BeanUtils.copyProperties(x, vo);
            List<String> labels = fillLabels(x);
            vo.setLabels(labels);
            return vo;
        }).collect(Collectors.toList());
        labelPageVO.setRecords(labelItems);
        return SingleResponse.of(labelPageVO);
    }

    @ApiOperation(value = "根据产品获取最近n天价格")
    @RequestMapping(value = "/{productId}/lately/priceList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<DayPriceVO>> listLatelyPrice(@PathVariable("productId") Long productId, Integer day) {
        if (Objects.isNull(day)) {
            day = 3;
        }
        List<DayPriceVO> dayPriceList = new ArrayList<>(day);
        List<Date> dayInParam = new ArrayList<>(day);
        Date current = DateUtil.beginOfDay(new Date());
        IntStream range = IntStream.range(0, day);
        range.forEach(i -> {
            DayPriceVO vo = new DayPriceVO();
            DateTime date = DateUtil.offsetDay(current, i);
            dayInParam.add(date);
            vo.setDay(date);
            vo.setEnabled(false);
            dayPriceList.add(vo);
        });
        Map<Date, ProductPriceEveryday> priceEverydayMap = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, productId)
                .eq(ProductPriceEveryday::getStatus, 0)
                .in(ProductPriceEveryday::getDate, dayInParam)
                .list()
                .stream()
                .map(x->{
                    ProductPriceEveryday everyday = new ProductPriceEveryday();
                    BeanUtils.copyProperties(x, everyday);
                    everyday.setDate(DateUtil.beginOfDay(x.getDate()));
                    return everyday;
                })
                .collect(Collectors.toMap(ProductPriceEveryday::getDate, Function.identity(), (v1, v2) -> v1));

        dayPriceList.forEach(x -> {
            ProductPriceEveryday productPriceEveryday = priceEverydayMap.get(x.getDay());
            if (Objects.nonNull(productPriceEveryday)) {
                Integer beforeDay = productPriceEveryday.getBeforeDay();
                DateTime latestBookDay = DateUtil.offsetDay(x.getDay(), -beforeDay);
                if (productPriceEveryday.getStockNumber().equals(-1L) || productPriceEveryday.getStockNumber() > 0) {
                    if (DateUtil.compare(current, latestBookDay) <= 0) {
                        x.setEnabled(true);
                        x.setId(productPriceEveryday.getId());
                        x.setStockModel(productPriceEveryday.getStockModel());
                    }
                }
                x.setPrice(productPriceEveryday.getSalePrice().toPlainString());
            }
        });

        return SingleResponse.of(dayPriceList);
    }

    @ApiOperation(value = "根据产品获取月价格列表")
    @RequestMapping(value = "/month/priceList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> listMonthPrice(ListMonthPriceParam param) {
        Integer month = param.getMonth();
        if (Objects.isNull(param.getMonth())) {
            month = Integer.parseInt(DateUtil.format(new Date(), "yyyyMM"));
        }
        Map<Date, ProductPriceEveryday> priceEverydayMap = productPriceEverydayService
                .lambdaQuery()
                .eq(ProductPriceEveryday::getMonth, month)
                .eq(ProductPriceEveryday::getScenicspotProductId, param.getScenicProductId())
                .eq(ProductPriceEveryday::getStatus, 0)
                .list()
                .stream()
                .map(x->{
                    ProductPriceEveryday everyday = new ProductPriceEveryday();
                    BeanUtils.copyProperties(x, everyday);
                    everyday.setDate(DateUtil.beginOfDay(x.getDate()));
                    return everyday;
                })
                .collect(Collectors.toMap(ProductPriceEveryday::getDate, Function.identity(), (v1, v2) -> v1));

        DateTime current = DateUtil.parse(month + "", "yyyyMM");
        DateTime beginOfMonth = DateUtil.beginOfDay(DateUtil.beginOfMonth(current));
        DateTime endOfMonth = DateUtil.beginOfDay(DateUtil.endOfMonth(current));
        List<DayPriceVO> dayPriceVOList = new ArrayList<>();
        while (DateUtil.compare(beginOfMonth, endOfMonth) <= 0) {
            DayPriceVO vo = new DayPriceVO();
            vo.setDay(beginOfMonth);
            vo.setEnabled(false);
            ProductPriceEveryday productPriceEveryday = priceEverydayMap.get(beginOfMonth);
            if (Objects.nonNull(productPriceEveryday)) {
                if (productPriceEveryday.getDate().compareTo(DateUtil.beginOfDay(new Date())) >= 0) {
                    Integer beforeDay = productPriceEveryday.getBeforeDay();
                    DateTime latestBookDay = DateUtil.offsetDay(vo.getDay(), -beforeDay);
                    if (productPriceEveryday.getStockNumber().equals(-1L) || productPriceEveryday.getStockNumber() > 0) {
                        if (DateUtil.compare(current, latestBookDay) <= 0) {
                            vo.setEnabled(true);
                            vo.setId(productPriceEveryday.getId());
                            vo.setStockModel(productPriceEveryday.getStockModel());
                        }
                    }
                }
                vo.setPrice(productPriceEveryday.getSalePrice().toPlainString());
            }
            dayPriceVOList.add(vo);
            beginOfMonth = DateUtil.offsetDay(beginOfMonth, 1);
        }
        return SingleResponse.of(dayPriceVOList);
    }

    @ApiOperation(value = "根据价格日历id获取场次列表")
    @RequestMapping(value = "/getSessionList/{dayId}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<DaySessionVO>> getSessionList(@PathVariable("dayId") Long dayId) {
        ProductPriceEveryday productPriceEveryday = productPriceEverydayService.getById(dayId);
        String dateStr = DateUtil.format(productPriceEveryday.getDate(), "yyyy-MM-dd");
        List<ProductDayTime> list = productDayTimeService.lambdaQuery()
                .eq(ProductDayTime::getProductDayId, dayId)
                .eq(ProductDayTime::getStatus, 0)
                .orderByAsc(ProductDayTime::getId)
                .list();
        List<DaySessionVO> listVO = list.stream().map(x -> {
            DaySessionVO vo = new DaySessionVO();
            BeanUtils.copyProperties(x, vo);
            DateTime endTime;
            if (Objects.nonNull(x.getEnd())) {
                endTime = DateUtil.parse(dateStr + " " + DateUtil.format(x.getEnd(), "HH:mm") + ":59", "yyyy-MM-dd HH:mm:ss");
                vo.setEnd(DateUtil.format(x.getEnd(), "HH:mm"));
            } else {
                endTime = DateUtil.parse(dateStr + " " + "00:00:59", "yyyy-MM-dd HH:mm:ss");
                vo.setEnd("00:00");
            }

            if (Objects.nonNull(x.getStart())) {
                vo.setStart(DateUtil.format(x.getStart(), "HH:mm"));
            } else {
                vo.setStart("00:00");
            }
            if (endTime.getTime() > (new Date()).getTime()) {
                vo.setEnabled(x.getStockNumber() > 0 || x.getStockNumber().equals(-1L));
            } else {
                vo.setEnabled(false);
            }
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(listVO);
    }

    @ApiOperation(value = "根据产品获取预定规则")
    @RequestMapping(value = "/{productId}/bookRule", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ProductRuleVO> getBookRule(@PathVariable("productId") Long productId) {
        ProductRuleVO vo = new ProductRuleVO();
        vo.setProductId(productId);
        ScenicspotProduct product = scenicspotProductService.getById(productId);
        Scenicspot scenicspot = scenicspotService.getById(product.getScenicspotId());

        ProductBaseInfoDTO productBaseInfoDTO = new ProductBaseInfoDTO();
        BeanUtils.copyProperties(product, productBaseInfoDTO);
        productBaseInfoDTO.setOfficeHours(scenicspot.getOpenTime());
        vo.setBaseInfo(productBaseInfoDTO);

        ProductBookRuleConfigDTO bookRuleConfigDTO = new ProductBookRuleConfigDTO();
        ScenicspotProductBookRuleConfig lastBookRuleConfig = scenicspotProductBookRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductBookRuleConfig::getScenicspotProductId, product.getId())
                .eq(ScenicspotProductBookRuleConfig::getVersion, product.getVersion())
                .orderByDesc(ScenicspotProductBookRuleConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastBookRuleConfig, bookRuleConfigDTO);
        vo.setBookConfig(bookRuleConfigDTO);

        ProductVerificationRuleConfigDTO verificationRuleConfigDTO = new ProductVerificationRuleConfigDTO();
        ScenicspotProductVerificationConfig lastVerificationConfig = scenicspotProductVerificationConfigService.lambdaQuery()
                .eq(ScenicspotProductVerificationConfig::getScenicspotProductId, product.getId())
                .eq(ScenicspotProductVerificationConfig::getVersion, product.getVersion())
                .orderByDesc(ScenicspotProductVerificationConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastVerificationConfig, verificationRuleConfigDTO);
        //qrCode|verificationCode|mobile|idCard
        String[] splitEntranceCertificate = verificationRuleConfigDTO.getEntranceCertificate().split("\\|");
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
        verificationRuleConfigDTO.setEntranceCertificateItems(certificateVoItems);
        vo.setVerificationConfig(verificationRuleConfigDTO);

        ProductRefundRuleConfigDTO refundRuleConfigDTO = new ProductRefundRuleConfigDTO();
        ScenicspotProductRefundRuleConfig lastRefundRuleConfig = scenicspotProductRefundRuleConfigService.lambdaQuery()
                .eq(ScenicspotProductRefundRuleConfig::getScenicspotProductId, product.getId())
                .eq(ScenicspotProductRefundRuleConfig::getVersion, product.getVersion())
                .orderByDesc(ScenicspotProductRefundRuleConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastRefundRuleConfig, refundRuleConfigDTO);
        vo.setRefundConfig(refundRuleConfigDTO);

        ProductValidPeriodConfigDTO validPeriodConfigDTO = new ProductValidPeriodConfigDTO();
        ScenicspotProductValidPeriodConfig lastValidPeriodConfig = scenicspotProductValidPeriodConfigService.lambdaQuery()
                .eq(ScenicspotProductValidPeriodConfig::getScenicspotProductId, product.getId())
                .eq(ScenicspotProductValidPeriodConfig::getVersion, product.getVersion())
                .orderByDesc(ScenicspotProductValidPeriodConfig::getVersion)
                .last("limit 1").one();
        BeanUtils.copyProperties(lastValidPeriodConfig, validPeriodConfigDTO);
        vo.setValidPeriodConfig(validPeriodConfigDTO);

        return SingleResponse.of(vo);
    }

    private List<String> fillLabels(UserProductPageVO x) {
        List<String> labels = new ArrayList<>();
        if (Objects.nonNull(x.getRefundMode())) {
            if (x.getRefundMode().equals(0)) {
                labels.add("随心退");
            } else if (x.getRefundMode().equals(1)) {
                labels.add("不可退");
            }
        }
        if (Objects.nonNull(x.getEntranceMode())) {
            if (x.getEntranceMode().equals(0)) {
                labels.add("无需换票");
            } else if (x.getEntranceMode().equals(1)) {
                labels.add("换票入园");
            }
        }
        return labels;
    }
}
