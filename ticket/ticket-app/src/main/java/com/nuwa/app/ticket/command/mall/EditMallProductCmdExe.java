package com.nuwa.app.ticket.command.mall;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.mall.EditMallProductCmd;
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
import java.util.Objects;

@Slf4j
@Component
public class EditMallProductCmdExe extends AbstractCmdExe<EditMallProductCmd, SingleResponse> {

    @Autowired
    private MallProductService productService;
    @Autowired
    private MallProductSkuStockService productSkuStockService;
    @Autowired
    private MallStoreService storeService;

    @Override
    protected SingleResponse handle(EditMallProductCmd cmd) {
        CreateMallProductCO co = cmd.getCreateMallProductCO();
        MallProduct one = productService.lambdaQuery().eq(MallProduct::getAppId, cmd.getAppId())
                .eq(MallProduct::getMchId,cmd.getUserAware().getMchId())
                .eq(MallProduct::getId, cmd.getId()).one();
        if (Objects.isNull(one)) {
            return ErrorEnum.FAILED.buildFailure();
        }
        MallProduct entity = new MallProduct();
        BeanUtil.copyProperties(co, entity);
        entity.setMchId(cmd.getUserAware().getMchId());
        entity.setAppId(cmd.getUserAware().getMchAppId());
        entity.setId(cmd.getId());
        StringBuilder specificationsIds = new StringBuilder();
        StringBuilder storeId = new StringBuilder();
        List<CreateMallProductSkuCO> specificationsId = co.getSpecificationsId();
        List<CreateMallStoreCO> storeAddParamList = co.getStoreAddParamList();

        ArrayList<Long> lowPrices = new ArrayList<>();
        if (specificationsId.size() > 0) {
            for (CreateMallProductSkuCO productSkuStockAddParam : specificationsId) {
                if (productSkuStockAddParam.getId() != null && productSkuStockAddParam.getId() != "") {
                    MallProductSkuStock productSkuStock = new MallProductSkuStock();

                    long marketPrice = (long) (productSkuStockAddParam.getMarketPrice() * 100);
                    long sellPrice = (long) (productSkuStockAddParam.getSellPrice() * 100);

                    productSkuStockAddParam.setMarketPrice(null);
                    productSkuStockAddParam.setSellPrice(null);

                    BeanUtil.copyProperties(productSkuStockAddParam, productSkuStock);

                    productSkuStock.setMarketPrice(marketPrice);
                    productSkuStock.setSellPrice(sellPrice);

                    productSkuStock.setMchId(cmd.getUserAware().getMchId());

                    productSkuStock.setId(Long.valueOf(productSkuStockAddParam.getId()));
                    specificationsIds.append(productSkuStockAddParam.getId()).append(",");
                    productSkuStockService.updateById(productSkuStock);
                    lowPrices.add(productSkuStock.getSellPrice());
                } else {
                    MallProductSkuStock productSkuStock = new MallProductSkuStock();

                    long marketPrice = (long) (productSkuStockAddParam.getMarketPrice() * 100);
                    long sellPrice = (long) (productSkuStockAddParam.getSellPrice() * 100);

                    productSkuStockAddParam.setMarketPrice(null);
                    productSkuStockAddParam.setSellPrice(null);

                    BeanUtil.copyProperties(productSkuStockAddParam, productSkuStock);

                    productSkuStock.setMchId(cmd.getUserAware().getMchId());

                    productSkuStock.setMarketPrice(marketPrice);
                    productSkuStock.setSellPrice(sellPrice);

                    productSkuStock.setProductId(one.getId().toString());
                    productSkuStockService.save(productSkuStock);
                    specificationsIds.append(productSkuStock.getId()).append(",");

                    lowPrices.add(productSkuStock.getSellPrice());
                }
            }
        }

        if (storeAddParamList.size() > 0) {
            for (CreateMallStoreCO storeAddParam : storeAddParamList) {
                MallStore store = new MallStore();
                BeanUtil.copyProperties(storeAddParam, store);
                store.setMchId(cmd.getUserAware().getMchId());
                store.setAppId(cmd.getUserAware().getMchAppId());
                storeService.save(store);
                storeId.append(store.getId()).append(",");
            }
        }
        entity.setLowPrice(Collections.min(lowPrices));
        specificationsIds.deleteCharAt(specificationsIds.length() - 1);
        storeId.deleteCharAt(storeId.length() - 1);
        entity.setSpecificationsId(specificationsIds.toString());
        entity.setStoreId(storeId.toString());
        return productService.updateById(entity) ? SingleResponse.of(entity) : ErrorEnum.FAILED.buildFailure();
    }
}
