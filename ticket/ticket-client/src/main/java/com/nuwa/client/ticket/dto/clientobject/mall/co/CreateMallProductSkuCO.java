package com.nuwa.client.ticket.dto.clientobject.mall.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "添加规格")
public class CreateMallProductSkuCO extends NuwaCO {
    /**
     *  商品规格主键id
     */
    @ApiModelProperty(value = "商品规格主键id")
    private String id;
    /**
     *  商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productId;

    /**
     *  规格名称
     */
    @ApiModelProperty(value = "规格名称")
    private String skuStockName;

    /**
     *  库存
     */
    @ApiModelProperty(value = "库存")
    private Integer stock;

    /**
     *  门市价
     */
    @ApiModelProperty(value = "门市价")
    private Double marketPrice;

    /**
     *  销售价
     */
    @ApiModelProperty(value = "销售价")
    private Double sellPrice;

    /**
     *  规格图
     */
    @ApiModelProperty(value = "规格图")
    private String skuStockImg;
}
