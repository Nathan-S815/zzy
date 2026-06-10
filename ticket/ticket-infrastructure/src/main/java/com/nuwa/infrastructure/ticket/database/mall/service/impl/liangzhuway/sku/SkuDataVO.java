package com.nuwa.infrastructure.ticket.database.mall.service.impl.liangzhuway.sku;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-03-10 16:48:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SkuDataVO {

    private Long id;
    private String productId;
    private String skuStockName;
    private int stock;
    /**
     * 市场价
     */
    @JsonSerialize(using = PriceJson.class)
    private Long marketPrice;
    /**
     *  销售价
     */
    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = PriceJson.class)
    private Long sellPrice;

    @ApiModelProperty(value = "图片")
    @JsonSerialize(using = MaterialJson.class)
    private String skuStockImg;

}