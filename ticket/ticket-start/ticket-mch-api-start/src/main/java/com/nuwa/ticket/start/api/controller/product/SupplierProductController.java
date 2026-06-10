package com.nuwa.ticket.start.api.controller.product;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.ticket.command.query.MchDistributeProductPageJoinQry;
import com.nuwa.app.ticket.command.query.MchProductPageJoinQry;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantGetSupplierPageQry;
import com.nuwa.client.zeus.api.merchant.MerchantClientI;
import com.nuwa.client.zeus.api.merchant.vo.MerchantSimpleVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.order.param.GetSupplierPageParam;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductDistributeJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductDistributeQuery;
import com.nuwa.infrastructure.ticket.database.product.mapper.query.PageByMerchantProductQuery;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductDistributePageVO;
import com.nuwa.infrastructure.ticket.database.product.vo.MerchantProductPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.ticket.start.api.biz.ProductBiz;
import com.nuwa.ticket.start.api.constants.LogRecordCategoryConstant;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.product.param.*;
import com.nuwa.ticket.start.api.controller.product.vo.DayTimeVO;
import com.nuwa.ticket.start.api.controller.product.vo.PriceEveryDayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/supplier")
@Api(tags = {"供应商-产品管理相关"})
public class SupplierProductController {

    @Autowired
    private ProductBiz productBiz;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

    @Autowired
    private MerchantProductJoinMapper merchantProductJoinMapper;

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
    private MerchantSaasSupplierService merchantSaasSupplierService;

    @Autowired
    private MerchantProductDistributeService merchantProductDistributeService;

    @Autowired
    private MerchantProductDistributeJoinMapper merchantProductDistributeJoinMapper;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private MerchantClientI merchantClientI;

    @ApiOperation(value = "我的产品分页查询")
    @RequestMapping(value = "my/product/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantProductPageVO>> selectByPage(MchProductPageJoinQry query, UserAware userAware) {
        PageByMerchantProductQuery queryPage = new PageByMerchantProductQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setMerchantId(userAware.getMchId());
        Page<MerchantProductPageVO> pageData = merchantProductJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "我分销的产品分页查询")
    @RequestMapping(value = "/product/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantProductDistributePageVO>> currentPage(MchDistributeProductPageJoinQry query, UserAware userAware) {
        PageByMerchantProductDistributeQuery queryPage = new PageByMerchantProductDistributeQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setSupplierMchId(userAware.getMchId());
        Page<MerchantProductDistributePageVO> pageData = merchantProductDistributeJoinMapper.paginateByQuery(queryPage);
        Set<Long> setMerchantIds = pageData.getRecords().stream().map(MerchantProductDistributePageVO::getDistributeMerchantId).filter(Objects::nonNull).collect(Collectors.toSet());
        Map<Long, MerchantSimpleVO> merchantInfoMap = loadMerchantInfoByIds(setMerchantIds);
        pageData.getRecords().forEach(x -> {
            MerchantSimpleVO merchantSimpleVO = merchantInfoMap.get(x.getDistributeMerchantId());
            if (Objects.nonNull(merchantSimpleVO)) {
                x.setDistributeMerchantId(x.getDistributeMerchantId());
                x.setDistributeName(merchantSimpleVO.getMchName());
                x.setDistributeLinkMobile(merchantSimpleVO.getLinkMobile());
                x.setDistributeLinkName(merchantSimpleVO.getLinkMan());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "产品-新增")
    @RequestMapping(value = "product/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody @Valid SaveScenicspotProductParam form, UserAware userAware) {
        Boolean ret = productBiz.saveProduct(form);
        if (ret) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品新增失败");
    }

    @LogRecordAnnotation(
            fail = "产品修改操作失败，原因：「{{#_errorMsg}}」",
            success = "产品修改操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            bizNo = "{{#form.productId}}",
            category = LogRecordCategoryConstant.MODIFY_PRODUCT,
            detail = "{{#form.toJson()}}")
    @ApiOperation(value = "产品-修改")
    @RequestMapping(value = "product/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid ModifyScenicspotProductParam form, UserAware userAware) {
        Boolean ret = productBiz.modifyProduct(form);
        if (ret) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品修改失败");
    }

    @LogRecordAnnotation(
            fail = "产品上架操作失败，原因：「{{#_errorMsg}}」",
            success = "产品上架操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            category = LogRecordCategoryConstant.PRODUCT_ON,
            bizNo = "{{#form.productId}}")
    @ApiOperation(value = "产品-上架")
    @RequestMapping(value = "product/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@RequestBody @Valid ProductOnLineParam form, UserAware userAware) {
        boolean update = scenicspotProductService.lambdaUpdate()
                .set(ScenicspotProduct::getStatus, 1)
                .set(ScenicspotProduct::getPublishTime, new Date())
                .set(ScenicspotProduct::getLastUpdateById, userAware.getUserId())
                .set(ScenicspotProduct::getLastUpdateByName, userAware.getUserName())
                .set(ScenicspotProduct::getLastUpdateTime, new Date())
                .eq(ScenicspotProduct::getId, form.getProductId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品上架失败");
    }

    @LogRecordAnnotation(
            fail = "产品下架操作失败，原因：「{{#_errorMsg}}」",
            success = "产品下架操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            category = LogRecordCategoryConstant.PRODUCT_OFF,
            bizNo = "{{#form.productId}}")
    @ApiOperation(value = "产品-下架")
    @RequestMapping(value = "product/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@RequestBody @Valid ProductOffLineParam form, UserAware userAware) {
        boolean update = scenicspotProductService.lambdaUpdate()
                .set(ScenicspotProduct::getStatus, 0)
                .set(ScenicspotProduct::getLastUpdateById, userAware.getUserId())
                .set(ScenicspotProduct::getLastUpdateByName, userAware.getUserName())
                .set(ScenicspotProduct::getLastUpdateTime, new Date())
                .eq(ScenicspotProduct::getId, form.getProductId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品下架失败");
    }

    @LogRecordAnnotation(
            fail = "产品修改权重操作失败，原因：「{{#_errorMsg}}」",
            success = "产品修改权重操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            bizNo = "{{#form.productId}}",
            category = LogRecordCategoryConstant.MODIFY_PRODUCT_WEIGHT,
            detail = "{{#form.toJson()}}")
    @ApiOperation(value = "产品-修改权重")
    @RequestMapping(value = "product/modify/weight", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyWeight(@RequestBody @Valid UpdateWeightProductParam form, UserAware userAware) {
        boolean update = scenicspotProductService.lambdaUpdate()
                .set(ScenicspotProduct::getWeight, form.getWeight())
                .set(ScenicspotProduct::getLastUpdateById, userAware.getUserId())
                .set(ScenicspotProduct::getLastUpdateByName, userAware.getUserName())
                .set(ScenicspotProduct::getLastUpdateTime, new Date())
                .eq(ScenicspotProduct::getId, form.getProductId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "修改权重失败");
    }

    @LogRecordAnnotation(
            fail = "价格日历设置操作失败，原因：「{{#_errorMsg}}」",
            success = "价格日历设置操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            bizNo = "{{#form.scenicspotProductId}}",
            category = LogRecordCategoryConstant.MODIFY_PRODUCT_PRICE,
            detail = "{{#form.toJson()}}")
    @ApiOperation(value = "产品-价格日历设置")
    @RequestMapping(value = "product/dayPrice/set", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> priceEverydaySet(@RequestBody @Valid DayPriceSetParam form, UserAware userAware) {
        Boolean ret = productBiz.priceEverydaySet(form);
        if (ret) {
            modifyPoiMinPrice(form.getScenicspotProductId());
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "产品场次设置失败");
    }

    @LogRecordAnnotation(
            fail = "价格日历删除操作失败，原因：「{{#_errorMsg}}」",
            success = "价格日历删除操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            category = LogRecordCategoryConstant.REMOVE_PRODUCT_PRICE,
            bizNo = "{{#form.scenicspotProductId}}",
            detail = "{{#form.toJson()}}")
    @ApiOperation(value = "产品-价格日历删除")
    @RequestMapping(value = "product/dayPrice/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> priceEverydayRemove(@RequestBody @Valid DayPriceRemoveParam form, UserAware userAware) {
        boolean update = productPriceEverydayService.lambdaUpdate()
                .set(ProductPriceEveryday::getStatus, 1)
                .set(ProductPriceEveryday::getLastUpdateTime, new Date())
                .set(ProductPriceEveryday::getLastUpdateById, userAware.getUserId())
                .set(ProductPriceEveryday::getLastUpdateByName, userAware.getUserName())
                .eq(ProductPriceEveryday::getScenicspotProductId, form.getScenicspotProductId())
                .in(ProductPriceEveryday::getDate, form.getDayList())
                .update();
        if (update) {
            productDayTimeService.lambdaUpdate()
                    .set(ProductDayTime::getStatus, 1)
                    .set(ProductDayTime::getLastUpdateTime, new Date())
                    .set(ProductDayTime::getLastUpdateById, userAware.getUserId())
                    .set(ProductDayTime::getLastUpdateByName, userAware.getUserName())
                    .eq(ProductDayTime::getScenicspotProductId, form.getScenicspotProductId())
                    .in(ProductDayTime::getDate, form.getDayList())
                    .update();
            modifyPoiMinPrice(form.getScenicspotProductId());
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "价格日历删除失败");
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

    @ApiOperation(value = "获取产品操作日志")
    @RequestMapping(value = "{productId}/operateLog/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<OperateLog>> operateLogList(@PathVariable("productId") Long productId, UserAware userAware) {
        List<OperateLog> list = operateLogService.lambdaQuery()
                .select(OperateLog::getOperatorUserName, OperateLog::getAction, OperateLog::getCategory, OperateLog::getCreateTime, OperateLog::getDetail)
                .eq(OperateLog::getBizKey, LogRecordPrefixConstant.SCENICSPOT_PRODUCT + "_" + productId)
                .orderByDesc(OperateLog::getId)
                .last("limit 10")
                .list();
        return SingleResponse.of(list);
    }

    @LogRecordAnnotation(
            fail = "产品删除操作失败，原因：「{{#_errorMsg}}」",
            success = "产品删除操作成功",
            operator = "{{#userAware.userName}}({{#userAware.mchUserId}})",
            prefix = LogRecordPrefixConstant.SCENICSPOT_PRODUCT,
            bizNo = "{{#productId}}",
            category = LogRecordCategoryConstant.REMOVE_PRODUCT,
            detail = "{{#productId}}")
    @ApiOperation(value = "产品删除")
    @RequestMapping(value = "{productId}/remove", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> removeProduct(@PathVariable("productId") Long productId, UserAware userAware) {
        scenicspotProductService.lambdaUpdate()
                .set(ScenicspotProduct::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
                .set(ScenicspotProduct::getLastUpdateTime, new Date())
                .eq(ScenicspotProduct::getId, productId).update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取分销商列表")
    @RequestMapping(value = "/distribute/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantSaasSupplier>> supplierPage(@Valid MerchantGetSupplierPageQry pageQry, UserAware userAware) {
        GetSupplierPageParam pageParam = new GetSupplierPageParam(pageQry);
        pageQry.setSupplierMerchantId(userAware.getMchId());
        IPage<MerchantSaasSupplier> pageData = merchantSaasSupplierService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取供应商列表")
    @RequestMapping(value = "supplier/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MerchantSupplierConfig>> listSupplier(UserAware userAware) {
        List<MerchantSupplierConfig> list = merchantSupplierConfigService.lambdaQuery()
                .eq(!userAware.getMchId().equals(-1L), MerchantSupplierConfig::getMerchantId, userAware.getMchId())
                .eq(MerchantSupplierConfig::getStatus, 1)
                .list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "产品-审核通过")
    @RequestMapping(value = "product/auditPass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditPass(@RequestBody @Valid SupplierProductAuditPassParam form, UserAware userAware) {
        Date current = new Date();
        boolean update = merchantProductDistributeService.lambdaUpdate()
                .set(MerchantProductDistribute::getAuditStatus, 1)
                .set(MerchantProductDistribute::getRejectReason, form.getRemark())
                .set(MerchantProductDistribute::getLastUpdateById, userAware.getUserId())
                .set(MerchantProductDistribute::getLastUpdateByName, userAware.getUserName())
                .set(MerchantProductDistribute::getLastUpdateTime, current)
                .eq(MerchantProductDistribute::getId, form.getProductDistributeId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "审核通过操作失败");
    }

    @ApiOperation(value = "产品-审核拒绝")
    @RequestMapping(value = "product/auditReject", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditReject(@RequestBody @Valid SupplierProductAuditRejectParam form, UserAware userAware) {
        Date current = new Date();
        boolean update = merchantProductDistributeService.lambdaUpdate()
                .set(MerchantProductDistribute::getAuditStatus, 2)
                .set(MerchantProductDistribute::getRejectReason, form.getReason())
                .set(MerchantProductDistribute::getLastUpdateById, userAware.getUserId())
                .set(MerchantProductDistribute::getLastUpdateByName, userAware.getUserName())
                .set(MerchantProductDistribute::getLastUpdateTime, current)
                .eq(MerchantProductDistribute::getId, form.getProductDistributeId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9001", "审核拒绝操作失败");
    }

    private void modifyPoiMinPrice(Long scenicspotProductId) {
        ProductPriceEveryday priceEveryday = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, scenicspotProductId)
                .ge(ProductPriceEveryday::getDate, DateUtil.beginOfDay(new Date()))
                .eq(ProductPriceEveryday::getStatus, 0)
                .orderByAsc(ProductPriceEveryday::getSalePrice)
                .last("limit 1")
                .one();
        if (Objects.nonNull(priceEveryday)) {
            ScenicspotProduct scenicspotProduct = scenicspotProductService.getById(scenicspotProductId);
            BigDecimal salePrice = priceEveryday.getSalePrice();
            BigDecimal marketPrice = priceEveryday.getMarketPrice();
            BigDecimal purchasePrice = priceEveryday.getPurchasePrice();
            scenicspotService.lambdaUpdate()
                    .set(Scenicspot::getPriceMin, salePrice)
                    .eq(Scenicspot::getId, scenicspotProduct.getScenicspotId())
                    .update();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPrice, salePrice)
                    .set(ScenicspotProduct::getLastUpdateTime, new Date())
                    .eq(ScenicspotProduct::getId, scenicspotProductId)
                    .update();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getMarketPrice, marketPrice)
                    .set(ScenicspotProduct::getLastUpdateTime, new Date())
                    .eq(ScenicspotProduct::getId, scenicspotProductId)
                    .update();

            scenicspotProductService.lambdaUpdate()
                    .set(ScenicspotProduct::getPurchasePrice, purchasePrice)
                    .set(ScenicspotProduct::getLastUpdateTime, new Date())
                    .eq(ScenicspotProduct::getId, scenicspotProductId)
                    .update();

        }
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
