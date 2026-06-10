package com.nuwa.infrastructure.ticket.database.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户场次模板表
 *
 * @author huyonghack@163.com
 * @since 2022-04-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantDayTimeTemplate对象")
public class MerchantDayTimeTemplate extends Model<MerchantDayTimeTemplate> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("模板名称")
    private String title;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("模板内容[{\"start\":\"09:30\",\"end\":\"10:30\",\"stock_number\":200}]")
    private String templateData;

    private String lastUpdateByName;

    private String createByName;

    private String createById;

    private Date lastUpdateTime;

    private String lastUpdateById;

    private Date createTime;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String MCH_ID = "mch_id";

    public static final String TEMPLATE_DATA = "template_data";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String CREATE_TIME = "create_time";



}
