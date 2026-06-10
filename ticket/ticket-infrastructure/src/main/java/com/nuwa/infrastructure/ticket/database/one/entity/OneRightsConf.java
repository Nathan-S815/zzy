package com.nuwa.infrastructure.ticket.database.one.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一码通商户端权益配置
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneRightsConf对象")
public class OneRightsConf extends Model<OneRightsConf> {
    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;

    @ApiModelProperty("备注信息")
    private String remark;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String VALIDITY_MODE = "validity_mode";

    public static final String VALIDITY_BEGIN_DATE = "validity_begin_date";

    public static final String VALIDITY_END_DATE = "validity_end_date";

    public static final String IDENTITY_CODE_LIST = "identity_code_list";

    public static final String RIGHTS_TYPE = "rights_type";

    public static final String DISCOUNT_VALUE = "discount_value";

    public static final String MCH_ID = "mch_id";

    public static final String SORT_NUM = "sort_num";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
