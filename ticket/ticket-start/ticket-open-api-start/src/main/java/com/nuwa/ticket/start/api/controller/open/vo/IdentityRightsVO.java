package com.nuwa.ticket.start.api.controller.open.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class IdentityRightsVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("有效期模式 range_date(日期范围) long_time(长期)")
    private String validityMode;

    @ApiModelProperty("开始日期")
    private Date validityBeginDate;

    @ApiModelProperty("结束日期")
    private Date validityEndDate;

    @ApiModelProperty("权益类型 discount(折扣)")
    private String rightsType;

    @ApiModelProperty("折扣值")
    private BigDecimal discountValue;
}
