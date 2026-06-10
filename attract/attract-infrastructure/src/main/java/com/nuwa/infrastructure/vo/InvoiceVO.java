package com.nuwa.infrastructure.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/11
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "发票上传")
public class InvoiceVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("团队ID")
    private Long teamId;

    @ApiModelProperty("开票人")
    private String userId;

    @ApiModelProperty("开票单位")
    private String invoiceUnit;

    @ApiModelProperty("开票时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date invoiceTime;

    @ApiModelProperty("发票号")
    private String invoiceCode;

    @ApiModelProperty("发票金额")
    private BigDecimal invoiceMoney;

    @ApiModelProperty("人数")
    private Long personNumber;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty("图片地址")
    @NotNull(message = "图片不能为空")
    private String invoiceUrl;

    @ApiModelProperty("备注")
    private String reamark;

    @ApiModelProperty("单据类型")
    private String invoiceType;
}
