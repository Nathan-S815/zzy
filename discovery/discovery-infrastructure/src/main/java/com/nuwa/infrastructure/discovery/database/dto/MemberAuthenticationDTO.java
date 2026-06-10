package com.nuwa.infrastructure.discovery.database.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MemberAuthenticationDTO {

    @ApiModelProperty("绑定记录id")
    private Long id;

    @ApiModelProperty("绑定主体id")
    private Long memberAccountBindId;

    @ApiModelProperty("审核状态 1：通过 2：不通过")
    private Integer status;

    @ApiModelProperty("图片 （认证失败时上传）")
    private String picture;

    @ApiModelProperty("认证失败原因")
    private String reason;

    @ApiModelProperty("达人id")
    private Long userId;

    @ApiModelProperty("达人标签id（审核成功时上传）")
    private List<Long> tagIds;
}
