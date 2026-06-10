package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绑定景区
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenicspotBindParam extends NuwaCommand {

    @ApiModelProperty("景区id")
    @NotNull
    private Long scenicspotId;
}
