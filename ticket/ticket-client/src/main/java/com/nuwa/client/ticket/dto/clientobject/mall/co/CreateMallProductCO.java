package com.nuwa.client.ticket.dto.clientobject.mall.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "添加商品")
public class CreateMallProductCO extends NuwaCO {

    @ApiModelProperty(value = "商品名称")
    private String productName;

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
    private String coverImg;

    @ApiModelProperty(value = "轮播图")
    private String carouselImgs;

    @ApiModelProperty(value = "客服电话")
    private String servicePhone;

    @ApiModelProperty(value = "线下门店信息")
    private List<CreateMallStoreCO> storeAddParamList;

    @ApiModelProperty(value = "规格Id(多个id逗号分隔)")
    private List<CreateMallProductSkuCO> specificationsId;

    @ApiModelProperty(value = "物流配置  0包邮1到付")
    private Integer expressType;

    @ApiModelProperty(value = "商品介绍")
    private String commodityIntroduce;

    @ApiModelProperty(value = "上下架状态  10上架11下架")
    private Integer publishStatus;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "最低价格")
    private Double lowPrice;

    @ApiModelProperty(value = "省市区地址")
    private String departurePlace;

    @ApiModelProperty(value = "一级分类ID")
    private Long classificationFirstId;

    @ApiModelProperty(value = "二级分类ID")
    private Long classificationSecondId;

    @ApiModelProperty(value = "三级分类ID")
    private Long classificationThirdId;

}


