package com.nuwa.ticket.start.api.controller.product;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.biz.ProductBiz;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.dto.*;
import com.nuwa.ticket.start.api.controller.product.vo.ScenicspotProductDetailVO;
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

import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/product")
@Api(tags = {"产品管理相关"})
public class ProductController {

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
    private OperateLogService operateLogService;

    @ApiOperation(value = "产品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicspotProductDetailVO> detail(@PathVariable("id") Long id, UserAware userAware) {
        ScenicspotProductDetailVO vo = new ScenicspotProductDetailVO();
        vo.setProductId(id);
        ScenicspotProduct product = scenicspotProductService.getById(id);

        ProductBaseInfoDTO productBaseInfoDTO = new ProductBaseInfoDTO();
        BeanUtils.copyProperties(product, productBaseInfoDTO);
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
}
