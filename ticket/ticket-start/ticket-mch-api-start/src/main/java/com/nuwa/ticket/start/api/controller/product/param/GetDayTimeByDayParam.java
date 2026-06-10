package com.nuwa.ticket.start.api.controller.product.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GetDayTimeByDayParam 按日获取场次列表")
public class GetDayTimeByDayParam extends NuwaCommand {

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    private Date day;

}
