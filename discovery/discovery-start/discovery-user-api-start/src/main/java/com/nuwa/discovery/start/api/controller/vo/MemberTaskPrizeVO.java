package com.nuwa.discovery.start.api.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MemberTaskPrizeVO {

    @ApiModelProperty("权益id")
    private Long id;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("任务权益id")
    private String prizeTypeId;

    @ApiModelProperty("审核文字备注")
    private String remarkText;

    @ApiModelProperty("审核图片备注")
    private String remarkPictures;

    @ApiModelProperty("任务状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;
}
