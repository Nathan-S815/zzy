package com.nuwa.infrastructure.discovery.database.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/2
 * @Description: C端任务列表
 */
@Data
public class MyTaskVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审核状态 1：待审核 2：审核成功 3：审核失败")
    private Integer auditStatus;
}
