package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 新增权益
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicspotAddRightsParam extends NuwaCommand {

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("权益id")
    private Long rightsId;

}
