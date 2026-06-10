/**
 * Copyright 2022 bejson.com
 */
package com.nuwa.infrastructure.ticket.database.mall.service.impl.liangzhuway.pro;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-03-10 13:31:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class DataVO {

    private Long id;
    private String productName;
    private String departurePlaceProvince;
    private String departurePlaceProvinceName;
    private String departurePlaceCity;
    private String departurePlaceCityName;
    private String departurePlaceCounty;
    private String departurePlaceCountyName;

    @ApiModelProperty(value = "封面图")
    @JsonSerialize(using = MaterialJson.class)
    private String coverImg;

    @ApiModelProperty(value = "轮播图")
    @JsonSerialize(using = MaterialJson.class)
    private String carouselImgs;

    @ApiModelProperty(value = "线下门店信息")
    private List<Stores> stores;
    @ApiModelProperty(value = "规格Id(多个id逗号分隔)")
    private String specificationsId;
    private String servicePhone;
    /**
     * 物流配置  0包邮1到付
     */
    private Integer expressType;

    private String commodityIntroduce;
    /**
     * 上下架状态  10上架11下架
     */
    private Integer publishStatus;
    /**
     * 销量
     */
    private Integer sales;

    /**
     * 最低价格
     */
    @JsonSerialize(using = PriceJson.class)
    private Long lowPrice;
    private String departurePlace;

}