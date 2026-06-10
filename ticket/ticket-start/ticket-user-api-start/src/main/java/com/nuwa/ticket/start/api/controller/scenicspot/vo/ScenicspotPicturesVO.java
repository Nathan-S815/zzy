package com.nuwa.ticket.start.api.controller.scenicspot.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hy
 */
@Data
public class ScenicspotPicturesVO {

    @ApiModelProperty("素材id")
    @JsonSerialize(using = MaterialJson.class)
    private Long materialId;

    @ApiModelProperty("标签名")
    private String label;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型,pic,video")
    private String type;
}
