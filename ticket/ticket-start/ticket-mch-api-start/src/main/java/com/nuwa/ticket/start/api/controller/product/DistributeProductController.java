package com.nuwa.ticket.start.api.controller.product;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.ticket.command.query.MchDistributeProductPageJoinQry;
import com.nuwa.app.ticket.command.query.MchProductPageJoinQry;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantGetSupplierPageQry;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.order.param.GetSupplierPageParam;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductCenterJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductDistributeJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductCenterQuery;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductDistributeQuery;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductDistributePageVO;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.biz.ProductBiz;
import com.nuwa.ticket.start.api.constants.LogRecordCategoryConstant;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.dto.AppendProductToDistributeDTO;
import com.nuwa.ticket.start.api.controller.product.param.*;
import com.nuwa.ticket.start.api.controller.product.vo.DayTimeVO;
import com.nuwa.ticket.start.api.controller.product.vo.PriceEveryDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/distribute")
@Api(tags = {"分销商-产品管理相关"})
public class DistributeProductController {
    @Autowired
    private ProductBiz productBiz;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private MerchantProductCenterJoinMapper merchantProductCenterJoinMapper;

    @Autowired
    private MerchantProductDistributeJoinMapper merchantProductDistributeJoinMapper;

    @Autowired
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private ScenicspotProductService scenicspotProductService;

    @Autowired
    private ScenicspotProductBookRuleConfigService scenicspotProductBookRuleConfigService;

    @Autowired
    private ScenicspotProductVerificationConfigService scenicspotProductVerificationConfigService;

    @Autowired
    private ScenicspotProductRefundRuleConfigService scenicspotProductRefundRuleConfigService;

    @Autowired
    private ScenicspotProductValidPeriodConfigService scenicspotProductValidPeriodConfigService;

    @Autowired
    private MerchantProductDistributeService merchantProductDistributeService;

    @Autowired
    private MerchantSaasSupplierService merchantSaasSupplierService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private MerchantClientI merchantClientI;

    @ApiOperation(value = "产品库分页查询")
    @RequestMapping(value = "/product/center/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantProductPageVO>> centerPage(MchProductPageJoinQry query, UserAware userAware) {
        PageByMerchantProductCenterQuery queryPage = new PageByMerchantProductCenterQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setHideSelected(false);
        queryPage.getExtMap().put("distributeMerchantId", userAware.getMchId() + "");
        Page<MerchantProductPageVO> pageData = merchantProductCenterJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            if (Objects.isNull(x.getProductDistributeId())) {
                x.setEnabled(true);
            } else {
                x.setEnabled(x.getAuditStatus().equals(2));
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "分销的产品分页查询")
    @RequestMapping(value = "/product/current/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantProductDistributePageVO>> currentPage(MchDistributeProductPageJoinQry query, UserAware userAware) {
        PageByMerchantProductDistributeQuery queryPage = new PageByMerchantProductDistributeQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setDistributeMchId(userAware.getMchId());
        queryPage.setSupplierMchId(query.getSupplierId());
        queryPage.setSupplierMchName(query.getSupplierName());
        Page<MerchantProductDistributePageVO> pageData = merchantProductDistributeJoinMapper.paginateByQuery(queryPage);
        Set<Long> setMerchantIds = pageData.getRecords().stream().map(MerchantProductDistributePageVO::getSupplierMerchantId).filter(Objects::nonNull).collect(Collectors.toSet());
        Map<Long, MerchantSimpleVO> merchantInfoMap = loadMerchantInfoByIds(setMerchantIds);
        pageData.getRecords().forEach(x -> {
            MerchantSimpleVO merchantSimpleVO = merchantInfoMap.get(x.getSupplierMerchantId());
            if (Objects.nonNull(merchantSimpleVO)) {
                x.setSupplierMerchantId(x.getSupplierMerchantId());
                x.setSupplierMerchantName(merchantSimpleVO.getMchName());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "加入到我的分销产品")
    @RequestMapping(value = "/product/appendToDistribute", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> appendToDistribute(@RequestBody @Valid ProductAppendToDistributeParam form, UserAware userAware) {
        AppendProductToDistributeDTO dto = new AppendProductToDistributeDTO();
        List<AppendProductToDistributeDTO.AppendProduct> collect = form.getProductIds().stream().map(x -> {
            ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(x);
            if (Objects.nonNull(scenicspotProduct)) {
                Integer count = merchantProductDistributeService.lambdaQuery()
                        .eq(MerchantProductDistribute::getProductId, x)
                        .ne(MerchantProductDistribute::getAuditStatus, 2)
                        .eq(MerchantProductDistribute::getDistributeMerchantId, userAware.getMchId())
                        .count();
                if (count > 0) {
                    return null;
                }
                AppendProductToDistributeDTO.AppendProduct appendProduct = new AppendProductToDistributeDTO.AppendProduct();
                List<Long> ids = new ArrayList<>();
                ids.add(scenicspotProduct.getMerchantId());
                ids.add(userAware.getMchId());
                SingleResponse<List<MerchantSimpleVO>> merchantListByIds = merchantClientI.getMerchantListByIds(ids);
                if (merchantListByIds.isSuccess()) {
                    List<MerchantSimpleVO> data = merchantListByIds.getData();
                    if (!data.isEmpty()) {
                        Map<Long, MerchantSimpleVO> mapMerchantData = data.stream().collect(Collectors.toMap(MerchantSimpleVO::getMchId, v -> v, (newVal, oldVal) -> newVal));
                        MerchantSimpleVO distributeMerchantSimpleVO = mapMerchantData.get(userAware.getMchId());
                        appendProduct.setDistributeMerchantId(userAware.getMchId());
                        appendProduct.setDistributeMerchantName(distributeMerchantSimpleVO.getMchName());
                        MerchantSimpleVO supplierMerchantSimpleVO = mapMerchantData.get(scenicspotProduct.getMerchantId());
                        appendProduct.setSupplierMerchantId(scenicspotProduct.getMerchantId());
                        appendProduct.setSupplierMerchantName(supplierMerchantSimpleVO.getMchName());
                        appendProduct.setProductId(x);
                    }
                }
                return appendProduct;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        dto.setProductList(collect);

        Boolean ret = productBiz.appendToDistribute(dto, userAware);
        if (ret) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "添加到我的分销失败");
    }

    @ApiOperation(value = "从分销产品中移除")
    @RequestMapping(value = "/product/removeToDistribute", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> removeToDistribute(@RequestBody @Valid ProductRemoveToDistributeParam form, UserAware userAware) {
        QueryWrapper<MerchantProductDistribute> removeWrapper = Wrappers.query();
        removeWrapper.eq(MerchantProductDistribute.DISTRIBUTE_MERCHANT_ID, userAware.getMchId());
        removeWrapper.in(MerchantProductDistribute.ID, form.getProductDistributeIds());
        boolean remove = merchantProductDistributeService.remove(removeWrapper);
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "移除分销产品失败");
    }

    @ApiOperation(value = "产品-下架")
    @RequestMapping(value = "product/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@RequestBody @Valid DistributeProductOffLineParam form, UserAware userAware) {
        Date current = new Date();
        boolean update = merchantProductDistributeService.lambdaUpdate()
                .set(MerchantProductDistribute::getPublishStatus, 0)
                .set(MerchantProductDistribute::getLastUpdateById, userAware.getUserId())
                .set(MerchantProductDistribute::getLastUpdateByName, userAware.getUserName())
                .set(MerchantProductDistribute::getLastUpdateTime, current)
                .set(MerchantProductDistribute::getPublishTime, current)
                .eq(MerchantProductDistribute::getId, form.getProductDistributeId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品下架失败");
    }

    @ApiOperation(value = "产品-上架")
    @RequestMapping(value = "product/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@RequestBody @Valid DistributeProductOnLineParam form, UserAware userAware) {
        Date current = new Date();
        boolean update = merchantProductDistributeService.lambdaUpdate()
                .set(MerchantProductDistribute::getPublishStatus, 1)
                .set(MerchantProductDistribute::getLastUpdateById, userAware.getUserId())
                .set(MerchantProductDistribute::getLastUpdateByName, userAware.getUserName())
                .set(MerchantProductDistribute::getLastUpdateTime, current)
                .set(MerchantProductDistribute::getPublishTime, current)
                .eq(MerchantProductDistribute::getId, form.getProductDistributeId())
                .eq(MerchantProductDistribute::getAuditStatus, 1)
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品上架失败");
    }

    @ApiOperation(value = "获取供应商分页查询")
    @RequestMapping(value = "/supplier/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantSaasSupplier>> supplierPage(@Valid MerchantGetSupplierPageQry pageQry, UserAware userAware) {
        GetSupplierPageParam pageParam = new GetSupplierPageParam(pageQry);
        pageQry.setDistributeMerchantId(userAware.getMchId());
        IPage<MerchantSaasSupplier> pageData = merchantSaasSupplierService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "产品-按月获取价格日历")
    @RequestMapping(value = "product/month/dayPrice/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<PriceEveryDayVO>> getPriceEverydayByMonth(@Valid GetDayPriceByMonthParam form, UserAware userAware) {
        List<ProductPriceEveryday> productPriceEverydayList = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getMonth, DateUtil.format(form.getMonth(), "yyyyMM"))
                .eq(ProductPriceEveryday::getScenicspotProductId, form.getScenicspotProductId())
                .eq(ProductPriceEveryday::getStatus, 0)
                .list();
        List<PriceEveryDayVO> voList = productPriceEverydayList.stream().map(x -> {
            PriceEveryDayVO vo = new PriceEveryDayVO();
            BeanUtils.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(voList);
    }

    @ApiOperation(value = "产品-修改权重")
    @RequestMapping(value = "product/modify/weight", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyWeight(@RequestBody @Valid DistributeUpdateWeightProductParam form, UserAware userAware) {
        boolean update = merchantProductDistributeService.lambdaUpdate()
                .set(MerchantProductDistribute::getWeight, form.getWeight())
                .set(MerchantProductDistribute::getLastUpdateById, userAware.getUserId())
                .set(MerchantProductDistribute::getLastUpdateByName, userAware.getUserName())
                .set(MerchantProductDistribute::getLastUpdateTime, new Date())
                .eq(MerchantProductDistribute::getId, form.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "修改权重失败");
    }

    @ApiOperation(value = "产品-按日获取场次列表")
    @RequestMapping(value = "product/dayTime/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<DayTimeVO>> getDayTimeByDay(@Valid GetDayTimeByDayParam form, UserAware userAware) {
        List<ProductDayTime> productPriceEverydayList = productDayTimeService.lambdaQuery()
                .eq(ProductDayTime::getDate, form.getDay())
                .eq(ProductDayTime::getStatus, 0)
                .eq(ProductDayTime::getScenicspotProductId, form.getScenicspotProductId())
                .orderByAsc(ProductDayTime::getId)
                .list();
        List<DayTimeVO> voList = productPriceEverydayList.stream().map(x -> {
            DayTimeVO vo = new DayTimeVO();
            BeanUtils.copyProperties(x, vo);
            if (Objects.isNull(x.getEnd())) {
                vo.setEnd("00:00");
            } else {
                vo.setEnd(DateUtil.format(x.getEnd(), "HH:mm"));
            }
            if (Objects.isNull(x.getStart())) {
                vo.setStart("00:00");
            } else {
                vo.setStart(DateUtil.format(x.getStart(), "HH:mm"));
            }
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(voList);
    }

    private Map<Long, MerchantSimpleVO> loadMerchantInfoByIds(Set<Long> setMapMchId) {
        Map<Long, MerchantSimpleVO> merchantMapData = new HashMap<>(10);
        if (setMapMchId.size() > 0) {
            SingleResponse<List<MerchantSimpleVO>> merchantResult = merchantClientI.getMerchantListByIds(setMapMchId);
            if (merchantResult.isSuccess()) {
                merchantMapData = merchantResult.getData().stream().collect(Collectors.toMap(MerchantSimpleVO::getMchId, x -> x));
            }
        }
        return merchantMapData;
    }
}
