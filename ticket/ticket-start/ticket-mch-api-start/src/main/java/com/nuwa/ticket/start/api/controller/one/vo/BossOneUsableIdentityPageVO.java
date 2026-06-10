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
public class BossOneUsableIdentityPageVO {

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

}
