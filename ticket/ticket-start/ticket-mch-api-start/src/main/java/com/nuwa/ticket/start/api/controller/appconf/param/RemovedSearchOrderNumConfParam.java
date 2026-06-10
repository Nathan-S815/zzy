package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RemovedSearchOrderNumConfParam 删除关键字")
public class RemovedSearchOrderNumConfParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;
}
