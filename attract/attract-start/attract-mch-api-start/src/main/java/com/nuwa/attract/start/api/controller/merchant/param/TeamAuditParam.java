package com.nuwa.attract.start.api.controller.merchant.param;

import javax.validation.constraints.NotNull;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审核请求参数
 *
 * @author nanHuang @南皇
 * @version com.nuwa.hotel.start.api.controller.param:AuditHotelPoiParam.java,v1.0.0 2022-08-02 10:33:53 nanHuang Exp $
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "团队审核通用参数")
public class TeamAuditParam extends NuwaCommand {
    @ApiModelProperty(value = "teamId", required = true)
    @NotNull(message = "teamId不能为空")
    private Long teamId;

    @ApiModelProperty(value = "teamUserRefId", required = true)
    private Long teamUserRefId;

    @ApiModelProperty(value = "实到人数")
    private Integer attendance;

    @ApiModelProperty(value = "酒店房间数")
    private Integer hotelRooms;

    @ApiModelProperty(value = "审核备注/原因")
    private String reason;
}
