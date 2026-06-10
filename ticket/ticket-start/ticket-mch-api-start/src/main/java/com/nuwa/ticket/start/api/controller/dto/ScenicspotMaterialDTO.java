package com.nuwa.ticket.start.api.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ScenicspotMaterialDTO {

    @ApiModelProperty(value = "素材id", required = true)
    @NotNull(message = "素材id不能为空")
    @JsonSerialize(using = MaterialJson.class)
    private Long materialId;

    @ApiModelProperty("标签名")
    private String label;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty(value = "类型,pic,video", required = true)
    @NotBlank(message = "类型不能为空")
    private String type;
}
