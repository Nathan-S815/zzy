package com.nuwa.infrastructure.ticket.database.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * 一码通功能链接配置
 *
 * @author huyonghack@163.com
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneClientLink对象")
public class OneClientLink extends Model<OneClientLink> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("功能名称")
    private String toolName;

    @ApiModelProperty("链接名称")
    private String linkName;

    @ApiModelProperty("url路径")
    private String linkUrl;

    @ApiModelProperty("跳转方式 inner_page(内部页面),outer_page(外部页面)")
    private String jumpType;

    @ApiModelProperty("排序字段 越小越靠前")
    private Integer sortNum;

    @ApiModelProperty("图标")
    @JsonSerialize(using = MaterialJson.class)
    private Long iconId;

    public static final String ID = "id";

    public static final String TOOL_NAME = "tool_name";

    public static final String LINK_NAME = "link_name";

    public static final String LINK_URL = "link_url";

    public static final String JUMP_TYPE = "jump_type";

    public static final String SORT_NUM = "sort_num";

    public static final String MCH_ID = "mch_id";

    public static final String ICON_ID = "icon_id";

}
