package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景区移除权益
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicspotRemoveRightsParam extends NuwaCommand {

    @ApiModelProperty("权益id")
    private Long id;

}
