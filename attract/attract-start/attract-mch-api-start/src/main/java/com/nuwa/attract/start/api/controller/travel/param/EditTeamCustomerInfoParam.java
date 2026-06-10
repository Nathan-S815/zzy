package com.nuwa.attract.start.api.controller.travel.param;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.travel.param:EditTeamCustomerInfoParam.java,v1.0.0 2022-09-16 14:31:26
 * nanHuang Exp $
 */
@Data
public class EditTeamCustomerInfoParam {
    @ApiModelProperty(value = "团队ID", required = true)
    @NotNull(message = "团队id不能为空")
    private Long teamId;

    @ApiModelProperty(value = " 景点/景区userId", required = true)
    @NotNull(message = "景点/景区userId")
    private Long userId;

    @ApiModelProperty(value = "游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    private Date travelDate;

    @ApiModelProperty(value = "可获取奖励的人数", required = true)
    private Integer rewardPerson;

    @ApiModelProperty(value = "去除的人 customerId集合")
    private List<Long> decrementCustomerId;

    @ApiModelProperty(value = "新增的人 customerId集合")
    private List<Long> incrementCustomerId;
}
