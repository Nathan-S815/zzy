package com.nuwa.client.ticket.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantAppPayInfoClientVO {
    @ApiModelProperty("本地id")
    private Long id;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("小程序模板id")
    private String appTemplateId;

    @ApiModelProperty("小程序三方应用id")
    private String thirdAppId;

    @ApiModelProperty("应用类型")
    private String appType;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("所属省份id")
    private Long provinceId;

    @ApiModelProperty("所属地市id")
    private Long cityId;

    @ApiModelProperty("抖音小程序的 APP Secret")
    private String secret;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("地市名称")
    private String cityName;

    @ApiModelProperty("类型 SINGLE_SCENIC:单景点 PLATE:全域")
    private String type;

    @ApiModelProperty("poi id")
    private Long poiId;

    @ApiModelProperty("poi id list")
    private String poiList;

    @ApiModelProperty("支付参数")
    private PaymentConfigVO paymentConfig;
}
