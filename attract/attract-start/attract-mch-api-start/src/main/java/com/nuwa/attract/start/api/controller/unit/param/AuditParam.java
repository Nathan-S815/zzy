package com.nuwa.attract.start.api.controller.unit.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 审核
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.unit.param:AuditParam.java,v1.0.0 2022-09-13 10:27:17 nanHuang Exp $
 */
@Data
@ApiModel(value = "审核参数")
@EqualsAndHashCode(callSuper = true)
public class AuditParam extends NuwaCommand {
    @ApiModelProperty(value = "userId", required = true)
    @NotNull(message = "userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "是否通过 true-审核通过 false-审核拒绝", required = true)
    @NotNull(message = "审核状态不能为空")
    private Boolean audit;
}
