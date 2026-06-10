package com.nuwa.infrastructure.discovery.database.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/6
 * @Description: 达人视频列表
 */
@Data
public class MemberVideoVO {

    @ApiModelProperty("上传人")
    private String createByName;

    @ApiModelProperty("视频")
    @JsonSerialize(using = MaterialJson.class)
    private String video;

    @ApiModelProperty("任务内容")
    private String taskName;
}
