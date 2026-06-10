package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 移除一码通广告
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientRemovedAdConfigParam extends NuwaCommand {

    @ApiModelProperty("广告id")
    @NotNull
    private Long adId;
}
