package com.nuwa.infrastructure.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/23
 * @Description: TODO
 */
@Data
public class TravelTeamVO {
    private static final long    serialVersionUID = 1L;

    @ApiModelProperty("团队id")
    private Long teamId;

    @ApiModelProperty("团队名")
    private String teamName;

    @ApiModelProperty("领队人姓名")
    private String leaderName;

    @ApiModelProperty("抵达单位名称")
    private String refMch;

    @ApiModelProperty("行程开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty("行程结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty("团队状态 0-未提交 1-景区酒店审核中 2-景区酒店审核失败 3-待文旅局审核 4-文旅局审核成功 5-文旅局审核失败")
    private Integer teamStatus;

    @ApiModelProperty("团队人数（总）")
    private Integer teamPerson;

    @ApiModelProperty("申请日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
}
