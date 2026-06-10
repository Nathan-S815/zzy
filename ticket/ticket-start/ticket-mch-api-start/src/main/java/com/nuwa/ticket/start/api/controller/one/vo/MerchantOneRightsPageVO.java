package com.nuwa.ticket.start.api.controller.one.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一码通可用身份认证配置
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneUsableIdentity对象")
public class MerchantOneRightsPageVO {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("景区id(-1代表通用权益)")
    private Long scenicspotId;

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

    @ApiModelProperty("排序字段 从低到高")
    private Integer sortNum;

}
