package com.nuwa.attract.start.api.controller.travel.param;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.travel.param:TravelTeamInfo.java,v1.0.0 2022-09-14 13:52:24
 * nanHuang Exp $
 */
@Data
public class TravelTeamInfo {
    private static final long  serialVersionUID = 1L;
    @ApiModelProperty("行程日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date          travelDate;
    @ApiModelProperty("景区/酒店用户id")
    private Long          userId;
    @ApiModelProperty("景区/酒店/自由行")
    @NotEmpty(message = "景区/酒店/自由行选择不能为空")
    private Integer          type;
}
