package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 一码通广告排序字段修改
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientModifyAdOrderNumParam extends NuwaCommand {

    @ApiModelProperty("广告id")
    @NotNull
    private Long adId;


    @ApiModelProperty("排序序号")
    @NotNull
    private Long orderNum;
}
