package com.nuwa.infrastructure.attract.database.invoice.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 
 *
 * @author nanhuang @南皇
 * @since 2022-11-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "发票实体类")
public class Invoice extends Model<Invoice> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("票据ID")
    @TableId(value = "invoice_id", type = IdType.AUTO)
    private Long invoiceId;

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

    @ApiModelProperty("单据类型")
    private Integer deleteFlag;

    public static final String INVOICE_ID = "invoice_id";

    public static final String TEAM_ID = "team_id";

    public static final String USER_ID = "user_id";

    public static final String INVOICE_UNIT = "invoice_unit";

    public static final String INVOICE_TIME = "invoice_time";

    public static final String INVOICE_CODE = "invoice_code";

    public static final String INVOICE_MONEY = "invoice_money";

    public static final String PERSON_NUMBER = "person_number";

    public static final String CREATE_TIME = "create_time";

    public static final String INVOICE_URL = "invoice_url";

    public static final String REAMARK = "reamark";

    public static final String INVOICE_TYPE = "invoice_type";

}
