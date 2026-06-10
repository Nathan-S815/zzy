package com.nuwa.infrastructure.discovery.database.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/21
 * @Description: TODO
 */
@Data
public class GreateVideoVO {

    @ApiModelProperty("视频")
    @JsonSerialize(using = MaterialJson.class)
    private String video;

    @ApiModelProperty("权重")
    private Integer weight;
}
