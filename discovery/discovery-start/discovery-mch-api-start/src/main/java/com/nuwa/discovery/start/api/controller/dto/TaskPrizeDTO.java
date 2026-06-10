package com.nuwa.discovery.start.api.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hy
 */
@Data
@AllArgsConstructor
public class TaskPrizeDTO {
    @ApiModelProperty("任务平台编码：douyin")
    private String platformCode;

    @ApiModelProperty("权益类型")
    private Integer prizeType;

    @ApiModelProperty("权益类型名称")
    private String prizeTypeName;

    @ApiModelProperty("权益名称")
    private String prizeTitle;

    @ApiModelProperty("权益内容")
    private String prizeContent;
}
