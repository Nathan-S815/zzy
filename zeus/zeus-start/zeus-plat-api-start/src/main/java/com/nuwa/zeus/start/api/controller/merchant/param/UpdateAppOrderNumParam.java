package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * <pre>
 * 修改商户应用排序
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改商户应用排序")
public class UpdateAppOrderNumParam extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id")
    private Long id;

    @ApiModelProperty(value = "排序字段", required = true)
    @NotNull(message = "orderNum")
    private Integer orderNum;

}
