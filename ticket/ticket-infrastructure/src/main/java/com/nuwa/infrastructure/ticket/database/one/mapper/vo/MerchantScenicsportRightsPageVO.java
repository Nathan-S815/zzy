package com.nuwa.infrastructure.ticket.database.one.mapper.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantScenicsportRightsPageVO {

    @ApiModelProperty("权益id")
    private Long id;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("景区id")
    private Long mchScenicspotId;

    @ApiModelProperty("景区权益id")
    private Long scenicspotRightsId;

    @ApiModelProperty("有效期模式 range_date(日期范围) long_time(长期)")
    private String validityMode;

    @ApiModelProperty("开始日期")
    private Date validityBeginDate;

    @ApiModelProperty("结束日期")
    private Date validityEndDate;

    @ApiModelProperty("支持的身份列表(逗号隔开,-1代表全部角色可用)")
    private String identityCodeList;

    @ApiModelProperty("权益类型 discount(折扣)")
    private String rightsType;

    @ApiModelProperty("折扣值")
    private BigDecimal discountValue;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("备注信息")
    private String remark;
}
