package com.nuwa.ticket.start.api.controller.user.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 删除联系人
 *
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemoveContactParam extends NuwaCommand {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
}
