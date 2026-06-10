package com.nuwa.ticket.start.api.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductBaseInfoDTO对象")
public class ProductBaseInfoDTO {

    @ApiModelProperty("所属景点id")
    private Long scenicspotId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("营业时间说明")
    private String officeHours;

    @ApiModelProperty("票种")
    private Integer ticketKind;

    @ApiModelProperty("费用说明")
    private String feeInfo;

    @ApiModelProperty("价格模式 （0:普通,1:日历）")
    private Integer priceMode;

    @ApiModelProperty("成人数量")
    private Integer adultNumber;

    @ApiModelProperty("儿童数量")
    private Integer childNumber;
}
