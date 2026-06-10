package com.nuwa.infrastructure.ticket.database.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一码通广告配置
 *
 * @author huyonghack@163.com
 * @since 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneAdConfig对象")
public class OneAdConfig extends Model<OneAdConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户Id")
    private Long mchId;

    @ApiModelProperty("一码通应用id")
    private Long oneClientId;

    @ApiModelProperty("广告名称")
    private String title;

    @ApiModelProperty("图片")
    @JsonSerialize(using = MaterialJson.class)
    private String image;

    @ApiModelProperty("跳转链接")
    private String link;

    @ApiModelProperty("跳转类型")
    private String linkType;

    @ApiModelProperty("排序字段")
    private Long orderNum;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String ONE_CLIENT_ID = "one_client_id";

    public static final String TITLE = "title";

    public static final String IMAGE = "image";

    public static final String LINK = "link";

    public static final String LINK_TYPE = "link_type";

    public static final String ORDER_NUM = "order_num";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
