package com.nuwa.attract.start.api.controller.reward.param;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward.param:CalculateRewardParam.java,v1.0.0 2022-09-20 13:51:05
 * nanHuang Exp $
 */
@Data
public class CalculateRewardParam {
    @ApiModelProperty(value = "teamId", required = true)
    @NotEmpty(message = "teamIds不能为空")
    private List<Long> teamIds;
}
