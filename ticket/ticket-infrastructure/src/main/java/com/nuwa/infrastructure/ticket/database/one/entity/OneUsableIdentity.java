package com.nuwa.infrastructure.ticket.database.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class OneUsableIdentity extends Model<OneUsableIdentity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("唯一编码")
    private String identityCode;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("图标id")
    @JsonSerialize(using = MaterialJson.class)
    private String iconId;

    @ApiModelProperty("链接")
    private String linkUrl;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("状态  on|off")
    private String status;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String IDENTITY_CODE = "identity_code";

    public static final String INTRODUCTION = "introduction";

    public static final String ICON_ID = "icon_id";

    public static final String LINK_URL = "link_url";

    public static final String MCH_ID = "mch_id";

    public static final String STATUS = "status";

}
