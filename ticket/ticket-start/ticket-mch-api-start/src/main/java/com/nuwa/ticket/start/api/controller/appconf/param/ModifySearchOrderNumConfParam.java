package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ModifySearchOrderNumConfParam 修改搜索排序字段")
public class ModifySearchOrderNumConfParam extends NuwaCommand {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("排序")
    private Integer orderNum;
}
