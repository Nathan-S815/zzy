package com.nuwa.app.ticket.command.mall;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.CreateMallProductCmd;
import com.nuwa.client.ticket.dto.clientobject.mall.co.CreateMallProductCO;
import com.nuwa.client.ticket.dto.clientobject.mall.co.CreateMallProductSkuCO;
import com.nuwa.client.ticket.dto.clientobject.mall.co.CreateMallStoreCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallStore;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallStoreService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class CreateMallProductCmdExe extends AbstractCmdExe<CreateMallProductCmd, SingleResponse> {

    @Autowired
    private MallProductService productService;
    @Autowired
    private MallProductSkuStockService productSkuStockService;
    @Autowired
    private MallStoreService storeService;

    @Override
    protected SingleResponse handle(CreateMallProductCmd cmd) {
        CreateMallProductCO co = cmd.getCreateMallProductCO();
        Integer count = productService.lambdaQuery().eq(MallProduct::getAppId, cmd.getAppId())
                .eq(MallProduct::getMchId,cmd.getUserAware().getMchId())
                .eq(MallProduct::getProductName, co.getProductName()).count();
        if (count > 0) {
            return ErrorEnum.REPEAT_PRODUCT.buildFailure();
        }
        MallProduct entity = new MallProduct();

        BeanUtil.copyProperties(co, entity);
        entity.setMchId(cmd.getUserAware().getMchId());
        entity.setAppId(cmd.getAppId());
        entity.setSpecificationsId(null);
        entity.setStoreId(null);
        boolean save = productService.save(entity);
        StringBuilder specificationsIds = new StringBuilder();
        StringBuilder storeId = new StringBuilder();
        boolean skuSave = true;
        ArrayList<Long> lowPrices = new ArrayList<>();
        if (save) {
            if (co.getSpecificationsId().size() > 0) {
                List<CreateMallProductSkuCO> specificationsId = co.getSpecificationsId();
                for (CreateMallProductSkuCO productSkuStockAddParam : specificationsId) {
                    MallProductSkuStock sukEntity = new MallProductSkuStock();

                    long marketPrice = (long) (productSkuStockAddParam.getMarketPrice() * 100);
                    long sellPrice = (long) (productSkuStockAddParam.getSellPrice() * 100);

                    productSkuStockAddParam.setMarketPrice(null);
                    productSkuStockAddParam.setSellPrice(null);

                    BeanUtil.copyProperties(productSkuStockAddParam, sukEntity);

                    sukEntity.setMarketPrice(marketPrice);
                    sukEntity.setSellPrice(sellPrice);

                    sukEntity.setProductId(entity.getId().toString());

                    sukEntity.setMchId(cmd.getUserAware().getMchId());

                    skuSave = productSkuStockService.save(sukEntity);
                    specificationsIds.append(sukEntity.getId()).append(",");
                    lowPrices.add(sukEntity.getSellPrice());
                }
            }
            if (co.getStoreAddParamList().size() > 0) {
                List<CreateMallStoreCO> storeAddParamList = co.getStoreAddParamList();
                for (CreateMallStoreCO storeAddParam : storeAddParamList) {
                    MallStore store = new MallStore();
                    BeanUtil.copyProperties(storeAddParam, store);
                    store.setMchId(cmd.getUserAware().getMchId());
                    store.setAppId(cmd.getAppId());
                    storeService.save(store);
                    storeId.append(store.getId()).append(",");
                }

            }
        }
        specificationsIds.deleteCharAt(specificationsIds.length() - 1);
        storeId.deleteCharAt(storeId.length() - 1);
        entity.setSpecificationsId(specificationsIds.toString());
        entity.setStoreId(storeId.toString());
        entity.setLowPrice(Collections.min(lowPrices));
        boolean update = false;
        if (save && skuSave) {
            update = productService.updateById(entity);
        }
        return update ? SingleResponse.of(entity) : ErrorEnum.FAILED.buildFailure();
    }
}
