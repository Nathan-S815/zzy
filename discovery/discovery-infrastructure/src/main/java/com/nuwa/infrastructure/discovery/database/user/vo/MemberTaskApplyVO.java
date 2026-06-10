package com.nuwa.infrastructure.discovery.database.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/15
 * @Description: TODO
 */
@Data
public class MemberTaskApplyVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("视频ID")
    private Long applyid;

    @ApiModelProperty("任务编号")
    private Long taskId;

    @ApiModelProperty("任务内容")
    private String name;

    @ApiModelProperty("上传人")
    private String createByName;

    @ApiModelProperty("上传时间")
    private Date createTime;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("视频")
    @JsonSerialize(using = MaterialJson.class)
    private String video;
}
