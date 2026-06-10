package com.nuwa.client.zeus.dto.clientobject.mch.co;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * OpenAppCO
 *
 * @author hy
 * @date 2021/6/3 9:37
 * @since 1.0.0
 */
@Data
public class OpenAppCO {

    @ApiModelProperty(value = "应用Id", required = true)
    @NotNull(message = "应用Id不能为空")
    private Integer appId;

    @ApiModelProperty(value = "依耐的应用", required = true)
    private List<OpenAppCO> dependentApps;
}
