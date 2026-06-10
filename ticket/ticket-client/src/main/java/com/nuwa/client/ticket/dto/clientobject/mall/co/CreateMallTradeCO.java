package com.nuwa.client.ticket.dto.clientobject.mall.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建订单")
public class CreateMallTradeCO extends NuwaCO {

    @ApiModelProperty(value = "产品ID")
    @NotNull(message = "产品ID 不能为空")
    private Long productId;

    @ApiModelProperty(value = "规格ID")
    @NotNull(message = "规格ID 不能为空")
    private Long specificationsId;

    @ApiModelProperty(value = "购买数量")
    @NotNull(message = "购买数量 不能为空")
    private Integer productNum;

/*    @ApiModelProperty(value = "buyerId")
    @NotNull(message = "buyerId 不能为空")
    private String buyerId;*/

    @ApiModelProperty(value = "支付宝appId")
    @NotNull(message = "支付宝appId 不能为空")
    private String appId;

    @ApiModelProperty(value = "收获方式 (10线上发货 11线下门店取货)")
    @NotNull(message = "收获方式 (10线上发货 11线下门店取货)")
    private Integer receivingMethod;

    @ApiModelProperty(value = "门店地址")
    @NotNull(message = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "门店经度")
    @NotNull(message = "门店经度")
    private String longitude;

    @ApiModelProperty(value = "门店纬度")
    @NotNull(message = "门店纬度")
    private String latitude;

    @ApiModelProperty(value = "收货人姓名")
    @NotNull(message = "收货人姓名")
    private String consigneeName;

    @ApiModelProperty(value = "收货人手机号")
    @NotNull(message = "收货人手机号")
    private String consigneeTel;

    @ApiModelProperty(value = "收货人地址")
    @NotNull(message = "收货人地址")
    private String consigneeAddr;

/*    @ApiModelProperty(value = "app 类型 1:微信 2:支付宝")
    @NotNull(message = "app 类型 1:微信 2:支付宝")
    private String appType;*/

    private String payType;

}


