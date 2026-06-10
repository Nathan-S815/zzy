package com.nuwa.discovery.start.api.controller.open.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommodityDataVO {

    @ApiModelProperty("发布视频数量")
    private Integer videoCount;

    @ApiModelProperty("探店数量")
    private Integer taskApplyCount;
}
