package com.nuwa.app.ticket.command.mall.query;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.client.ticket.dto.clientobject.mall.qry.MallProductQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProduct;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallProductSkuStock;
import com.nuwa.infrastructure.ticket.database.mall.entity.MallStore;
import com.nuwa.infrastructure.ticket.database.mall.service.MallClassificationService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallProductSkuStockService;
import com.nuwa.infrastructure.ticket.database.mall.service.MallStoreService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MallProductQryExe extends AbstractQryExe<MallProductQry, SingleResponse> {

    @Autowired
    private MallProductService productService;
    @Autowired
    private MallStoreService storeService;
    @Autowired
    private MallClassificationService classificationService;
    @Autowired
    private MallProductSkuStockService productSkuStockService;

    @Override
    protected SingleResponse handle(MallProductQry cmd) {
        if (Objects.isNull(cmd.getId())) {
            return ErrorEnum.MISSING_REQUIRED_PARAMS.buildFailure();
        }
        ProductVo vo = new ProductVo();
        MallProduct product = productService.getById(cmd.getId());
        BeanUtil.copyProperties(product, vo);
        List<MallStore> list = new ArrayList<>();
        Arrays.asList(product.getStoreId().split(",")).forEach(x->list.add(storeService.getById(x)));
        vo.setStores(list);
        if (BeanUtil.isNotEmpty(product.getClassificationFirstId())){
            vo.setClassificationFirst(classificationService.getById(product.getClassificationFirstId()).getClassificationName());
        }
        if (BeanUtil.isNotEmpty(product.getClassificationSecondId())){
            vo.setClassificationSecond(classificationService.getById(product.getClassificationSecondId()).getClassificationName());
        }
        if (BeanUtil.isNotEmpty(product.getClassificationThirdId())){
            vo.setClassificationThird(classificationService.getById(product.getClassificationThirdId()).getClassificationName());
        }

        List<MallProductSkuStock> skus = productSkuStockService.lambdaQuery().eq(MallProductSkuStock::getProductId, cmd.getId()).list();
        vo.setSkus(skus);
        return SingleResponse.of(vo);
    }

    @Data
    public static class ProductVo {

        @ApiModelProperty(value = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @ApiModelProperty(value = "商品名称")
        private String productName;

        @ApiModelProperty(value = "一级分类")
        private String classificationFirst;

        @ApiModelProperty(value = "二级分类")
        private String classificationSecond;

        @ApiModelProperty(value = "三级分类")
        private String classificationThird;

        @ApiModelProperty(value = "发货地省")
        private String departurePlaceProvince;

        @ApiModelProperty(value = "发货地省名称")
        private String departurePlaceProvinceName;

        @ApiModelProperty(value = "发货地市")
        private String departurePlaceCity;

        @ApiModelProperty(value = "发货地市名称")
        private String departurePlaceCityName;

        @ApiModelProperty(value = "发货地区")
        private String departurePlaceCounty;

        @ApiModelProperty(value = "发货地区名称")
        private String departurePlaceCountyName;

        @ApiModelProperty(value = "封面图")
        @JsonSerialize(using = MaterialJson.class)
        private String coverImg;

        @ApiModelProperty(value = "轮播图")
        @JsonSerialize(using = MaterialJson.class)
        private String carouselImgs;

        @ApiModelProperty(value = "线下门店信息")
        private List<MallStore> stores;

        @ApiModelProperty(value = "规格Id(多个id逗号分隔)")
        private List<MallProductSkuStock> skus;

        @ApiModelProperty(value = "客服电话")
        private String servicePhone;

        @ApiModelProperty(value = "物流配置  0包邮1到付")
        private Integer expressType;

        @ApiModelProperty(value = "商品介绍")
        private String commodityIntroduce;

        @ApiModelProperty(value = "上下架状态  10上架11下架")
        private Integer publishStatus;

        @ApiModelProperty(value = "销量")
        private Integer sales;

        @ApiModelProperty(value = "最低价格")
        @JsonSerialize(using = PriceJson.class)
        private Long lowPrice;

        @ApiModelProperty(value = "省市区地址")
        private String departurePlace;
    }
}
