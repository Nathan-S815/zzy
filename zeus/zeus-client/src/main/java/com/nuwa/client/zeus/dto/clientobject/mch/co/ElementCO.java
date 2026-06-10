package com.nuwa.client.zeus.dto.clientobject.mch.co;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class ElementCO {

    @ApiModelProperty(value = "menu button", required = true)
    private String type;

    @ApiModelProperty(value = "id", required = true)
    private Integer id;
}
