package com.nuwa.ticket.start.api.biz;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cola.exception.Assert;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.ticket.start.api.controller.dto.AppendProductToDistributeDTO;
import com.nuwa.ticket.start.api.controller.product.param.DayPriceSetParam;
import com.nuwa.ticket.start.api.controller.product.param.ModifyScenicspotProductParam;
import com.nuwa.ticket.start.api.controller.product.param.SaveScenicspotProductParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 产品管理
 *
 * @author hy
 */
@Slf4j
@Service
public class ProductBiz {

    @Autowired
    private ProductPriceEverydayService productPriceEverydayService;

    @Autowired
    private ProductDayTimeService productDayTimeService;

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
    private MerchantSupplierConfigService merchantSupplierConfigService;

    @Autowired
    private MerchantProductDistributeService merchantProductDistributeService;

    @Autowired
    private MerchantSaasSupplierService merchantSaasSupplierService;

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveProduct(SaveScenicspotProductParam param) {
        log.info(">>>> saveProduct");
        log.info("param:{}", param);
        UserAware userAware = param.getUserAware();
        Long version = 1L;
        ScenicspotProduct product = new ScenicspotProduct();
        BeanUtils.copyProperties(param.getBaseInfo(), product);
        product.setSalesMode(2);
        product.setMerchantId(userAware.getMchId());
        product.setCreateById(userAware.getUserId() + "");
        product.setCreateByName(userAware.getUserName());
        product.setCreateTime(new Date());
        product.setVersion(version);
        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(param.getBaseInfo().getSupplierId());
        product.setSupplierName(supplierConfig.getName());
        boolean productSaveFlag = product.insert();
        Assert.isTrue(productSaveFlag);
        log.info("save ScenicspotProduct[id:{}] success.", product.getId());
        Long productId = product.getId();

        ScenicspotProductBookRuleConfig bookRuleConfig = new ScenicspotProductBookRuleConfig();
        BeanUtils.copyProperties(param.getBookConfig(), bookRuleConfig);
        bookRuleConfig.setScenicspotProductId(productId);
        bookRuleConfig.setVersion(version);
        boolean bookRuleSaveFlag = bookRuleConfig.insert();
        Assert.isTrue(bookRuleSaveFlag);
        log.info("save ScenicspotProductBookRuleConfig[id:{}] success.", bookRuleConfig.getId());

        ScenicspotProductVerificationConfig verificationConfig = new ScenicspotProductVerificationConfig();
        BeanUtils.copyProperties(param.getVerificationConfig(), verificationConfig);
        verificationConfig.setScenicspotProductId(productId);
        verificationConfig.setVersion(version);
        boolean periodConfigSaveFlag = verificationConfig.insert();
        Assert.isTrue(periodConfigSaveFlag);
        log.info("save ScenicspotProductVerificationConfig[id:{}] success.", verificationConfig.getId());

        ScenicspotProductRefundRuleConfig refundRuleConfig = new ScenicspotProductRefundRuleConfig();
        BeanUtils.copyProperties(param.getRefundConfig(), refundRuleConfig);
        refundRuleConfig.setScenicspotProductId(productId);
        refundRuleConfig.setVersion(version);
        boolean refundRuleConfigSaveFlag = refundRuleConfig.insert();
        Assert.isTrue(refundRuleConfigSaveFlag);
        log.info("save ScenicspotProductRefundRuleConfig[id:{}] success.", refundRuleConfig.getId());

        ScenicspotProductValidPeriodConfig validPeriodConfig = new ScenicspotProductValidPeriodConfig();
        BeanUtils.copyProperties(param.getValidPeriodConfig(), validPeriodConfig);
        validPeriodConfig.setScenicspotProductId(productId);
        validPeriodConfig.setVersion(version);
        boolean validPeriodConfigSaveFlag = validPeriodConfig.insert();
        Assert.isTrue(validPeriodConfigSaveFlag);
        log.info("save ScenicspotProductValidPeriodConfig[id:{}] success.", validPeriodConfig.getId());

        log.info("<<<< saveProduct");
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyProduct(ModifyScenicspotProductParam param) {
        log.info(">>>> modifyProduct");
        log.info("param:{}", param);
        UserAware userAware = param.getUserAware();
        ScenicspotProduct product = scenicspotProductService.getById(param.getProductId());
        Long version = product.getVersion();
        Long newVersion = product.getVersion() + 1;
        BeanUtils.copyProperties(param.getBaseInfo(), product);
        product.setStatus(0);
        product.setMerchantId(userAware.getMchId());
        product.setLastUpdateById(userAware.getUserId() + "");
        product.setLastUpdateByName(userAware.getUserName());
        product.setLastUpdateTime(new Date());
        product.setVersion(newVersion);
        MerchantSupplierConfig supplierConfig = merchantSupplierConfigService.getById(param.getBaseInfo().getSupplierId());
        if(Objects.nonNull(supplierConfig)){
            product.setSupplierName(supplierConfig.getName());
        }
        boolean productSaveFlag = product.updateById();
        Assert.isTrue(productSaveFlag);
        log.info("update ScenicspotProduct[id:{}] success.", product.getId());
        Long productId = product.getId();

        ScenicspotProductBookRuleConfig bookRuleConfig = new ScenicspotProductBookRuleConfig();
        BeanUtils.copyProperties(param.getBookConfig(), bookRuleConfig);
        bookRuleConfig.setScenicspotProductId(productId);
        bookRuleConfig.setVersion(newVersion);
        boolean bookRuleSaveFlag = bookRuleConfig.insert();
        Assert.isTrue(bookRuleSaveFlag);
        log.info("save ScenicspotProductBookRuleConfig[id:{}] success.", bookRuleConfig.getId());

        ScenicspotProductVerificationConfig verificationConfig = new ScenicspotProductVerificationConfig();
        BeanUtils.copyProperties(param.getVerificationConfig(), verificationConfig);
        verificationConfig.setScenicspotProductId(productId);
        verificationConfig.setVersion(newVersion);
        boolean periodConfigSaveFlag = verificationConfig.insert();
        Assert.isTrue(periodConfigSaveFlag);
        log.info("save ScenicspotProductVerificationConfig[id:{}] success.", verificationConfig.getId());

        ScenicspotProductRefundRuleConfig refundRuleConfig = new ScenicspotProductRefundRuleConfig();
        BeanUtils.copyProperties(param.getRefundConfig(), refundRuleConfig);
        refundRuleConfig.setScenicspotProductId(productId);
        refundRuleConfig.setVersion(newVersion);
        boolean refundRuleConfigSaveFlag = refundRuleConfig.insert();
        Assert.isTrue(refundRuleConfigSaveFlag);
        log.info("save ScenicspotProductRefundRuleConfig[id:{}] success.", refundRuleConfig.getId());

        ScenicspotProductValidPeriodConfig validPeriodConfig = new ScenicspotProductValidPeriodConfig();
        BeanUtils.copyProperties(param.getValidPeriodConfig(), validPeriodConfig);
        validPeriodConfig.setScenicspotProductId(productId);
        validPeriodConfig.setVersion(newVersion);
        boolean validPeriodConfigSaveFlag = validPeriodConfig.insert();
        Assert.isTrue(validPeriodConfigSaveFlag);
        log.info("save ScenicspotProductValidPeriodConfig[id:{}] success.", validPeriodConfig.getId());

        log.info("<<<< modifyProduct");
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean priceEverydaySet(DayPriceSetParam param) {
        log.info(">>>> priceEverydaySet");
        log.info("param:{}", param);
        UserAware userAware = param.getUserAware();

        boolean update = scenicspotProductService.lambdaUpdate()
                .set(ScenicspotProduct::getStatus, 0)
                .set(ScenicspotProduct::getLastUpdateTime, new Date())
                .eq(ScenicspotProduct::getId, param.getScenicspotProductId())
                .update();

        Map<Date, Long> dayPriceMapData = productPriceEverydayService.lambdaQuery()
                .eq(ProductPriceEveryday::getScenicspotProductId, param.getScenicspotProductId())
                .in(ProductPriceEveryday::getDate, param.getDayList())
                .list()
                .stream()
                .collect(Collectors.toMap(ProductPriceEveryday::getDate, ProductPriceEveryday::getId));

        List<ProductPriceEveryday> priceEverydayList = param.getDayList().stream().map(day -> {
            ProductPriceEveryday productPriceEveryday = new ProductPriceEveryday();
            BeanUtils.copyProperties(param, productPriceEveryday);
            productPriceEveryday.setCreateTime(new Date());
            productPriceEveryday.setCreateById(userAware.getUserId() + "");
            productPriceEveryday.setCreateByName(userAware.getUserName());
            DateTime dayDate = DateUtil.parse(day, "yyyy-MM-dd");
            productPriceEveryday.setDate(dayDate);
            productPriceEveryday.setMonth(Integer.parseInt(DateUtil.format(dayDate, "yyyyMM")));
            Long id = dayPriceMapData.get(dayDate);
            if (Objects.nonNull(id)) {
                productPriceEveryday.setStatus(0);
                productPriceEveryday.setId(id);
                productPriceEveryday.setLastUpdateById(userAware.getMchId() + "");
                productPriceEveryday.setLastUpdateByName(userAware.getUserName());
                productPriceEveryday.setLastUpdateTime(new Date());
            }
            return productPriceEveryday;
        }).collect(Collectors.toList());

        boolean saveBatch = productPriceEverydayService.saveOrUpdateBatch(priceEverydayList);
        Assert.isTrue(saveBatch);
        log.info("saveBatch priceEverydayList[size:{}] success.", priceEverydayList.size());


        if (param.getStockModel().equals(1) && Objects.nonNull(param.getDayTimeList())) {
            priceEverydayList.forEach(day -> {

                productDayTimeService.lambdaUpdate()
                        .set(ProductDayTime::getStatus, 1)
                        .set(ProductDayTime::getLastUpdateTime, new Date())
                        .set(ProductDayTime::getLastUpdateById, userAware.getUserId())
                        .set(ProductDayTime::getLastUpdateByName, userAware.getUserName())
                        .eq(ProductDayTime::getDate, day.getDate())
                        .eq(ProductDayTime::getScenicspotProductId, day.getScenicspotProductId())
                        .update();

                List<ProductDayTime> productDayTimeBatch = param.getDayTimeList().stream().map(dayTime -> {
                    ProductDayTime productDayTime = new ProductDayTime();
                    BeanUtils.copyProperties(dayTime, productDayTime);
                    productDayTime.setProductDayId(day.getId());
                    productDayTime.setCreateTime(new Date());
                    productDayTime.setCreateById(userAware.getUserId() + "");
                    productDayTime.setCreateByName(userAware.getUserName());
                    productDayTime.setScenicspotProductId(param.getScenicspotProductId());
                    productDayTime.setDate(day.getDate());
                    productDayTime.setTitle(DateUtil.format(dayTime.getStart(),"HH:mm") + "~" + DateUtil.format(dayTime.getEnd(),"HH:mm"));
                    return productDayTime;
                }).collect(Collectors.toList());

                boolean b = productDayTimeService.saveBatch(productDayTimeBatch);
                Assert.isTrue(b);
                log.info("saveBatch productDayTimeBatch[size:{}] success.", productDayTimeBatch.size());
            });

        }

        log.info("<<<< priceEverydaySet");
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean appendToDistribute(AppendProductToDistributeDTO dto, UserAware userAware) {
        log.info(">>>> appendToDistribute");
        log.info("dto:{}", dto);

        List<AppendProductToDistributeDTO.AppendProduct> productList = dto.getProductList();
        List<MerchantProductDistribute> batchProductDistribute = productList.stream().map(x -> {
            List<MerchantProductDistribute> merchantProductDistributes = merchantProductDistributeService.lambdaQuery()
                    .eq(MerchantProductDistribute::getProductId, x.getProductId())
                    .eq(MerchantProductDistribute::getAuditStatus, 2)
                    .eq(MerchantProductDistribute::getDistributeMerchantId, userAware.getMchId())
                    .list();
            if (merchantProductDistributes.size() > 0) {
                MerchantProductDistribute oldMerchantProductDistribute = merchantProductDistributes.get(0);
                if (Objects.nonNull(oldMerchantProductDistribute)) {
                    oldMerchantProductDistribute.setAuditStatus(0);
                    oldMerchantProductDistribute.setPublishStatus(0);
                    return oldMerchantProductDistribute;
                }
            }

            MerchantProductDistribute merchantProductDistribute = new MerchantProductDistribute();
            merchantProductDistribute.setSupplierMerchantId(x.getSupplierMerchantId());
            merchantProductDistribute.setDistributeMerchantId(userAware.getMchId());
            merchantProductDistribute.setSupplierMerchantName(x.getSupplierMerchantName());
            merchantProductDistribute.setProductId(x.getProductId());
            merchantProductDistribute.setCreateById(userAware.getMchUserId() + "");
            merchantProductDistribute.setCreateByName(userAware.getUserName());
            merchantProductDistribute.setAuditStatus(0);
            merchantProductDistribute.setPublishStatus(0);
            merchantProductDistribute.setCreateTime(new Date());
            return merchantProductDistribute;
        }).collect(Collectors.toList());
        if (batchProductDistribute.size() > 0) {
            boolean saveBatch = merchantProductDistributeService.saveOrUpdateBatch(batchProductDistribute);
            Assert.isTrue(saveBatch, "saveBatch batchProductDistribute failed");
            log.info("saveBatch batchProductDistribute[size:{}] success.", batchProductDistribute.size());
        } else {
            return false;
        }

        List<MerchantSaasSupplier> batchMerchantSaasSupplier = productList.stream().map(x -> {
            Integer count = merchantSaasSupplierService.lambdaQuery()
                    .eq(MerchantSaasSupplier::getDistributeMerchantId, userAware.getMchId())
                    .eq(MerchantSaasSupplier::getSupplierMerchantId, x.getSupplierMerchantId())
                    .count();
            if (count > 0) {
                return null;
            }
            MerchantSaasSupplier saasSupplier = new MerchantSaasSupplier();
            saasSupplier.setSupplierName(x.getSupplierMerchantName());
            saasSupplier.setSupplierMerchantId(x.getSupplierMerchantId());
            saasSupplier.setCreateTime(new Date());
            saasSupplier.setDistributeMerchantId(userAware.getMchId());
            saasSupplier.setDistributeName(x.getDistributeMerchantName());
            return saasSupplier;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (batchMerchantSaasSupplier.size() > 0) {
            boolean saveBatch = merchantSaasSupplierService.saveBatch(batchMerchantSaasSupplier);
            Assert.isTrue(saveBatch, "saveBatch batchMerchantSaasSupplierItems failed");
            log.info("saveBatch batchMerchantSaasSupplierItems[size:{}] success.", batchMerchantSaasSupplier.size());
        }
        log.info("<<<< appendToDistribute");
        return Boolean.TRUE;
    }


}
