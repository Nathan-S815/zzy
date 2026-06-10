package com.nuwa.infrastructure.ticket.database.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hy
 */
@Data
public class MerchantProductPageVO {
    @ApiModelProperty("产品id")
    private Long id;

    @ApiModelProperty("所属景点id")
    private Long scenicspotId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("销售数量")
    private Integer sellNumber;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    private Integer status;

    @ApiModelProperty("景点POI名称")
    private String scenicspotName;

    @ApiModelProperty("上架时间")
    private Date publishTime;

    @ApiModelProperty("分销商产品表主键Id")
    private String productDistributeId;

    @ApiModelProperty("市场价格")
    private BigDecimal marketPrice;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("分销商审核状态")
    private Integer auditStatus;

    @ApiModelProperty("产品是否可选择")
    private Boolean enabled;
}
