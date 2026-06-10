package com.nuwa.attract.start.api.controller.reward.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 奖励计算
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward.vo:CalculateRewardVO.java,v1.0.0 2022-09-20 13:49:01
 * nanHuang Exp $
 */
@Data
public class CalculateRewardVO {
    private static final long    serialVersionUID = 1L;
    @ApiModelProperty("一日游奖励")
    private Integer oneDayReward;
    @ApiModelProperty("过夜游奖励")
    private Integer nightReward;
}
