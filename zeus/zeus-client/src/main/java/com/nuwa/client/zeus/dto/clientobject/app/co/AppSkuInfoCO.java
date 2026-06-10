package com.nuwa.client.zeus.dto.clientobject.app.co;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * OpenAppCO
 *
 * @author hy
 * @date 2021/6/3 9:37
 * @since 1.0.0
 */
@Data
public class AppSkuInfoCO {

    /**
     * 规格名称
     */
    @ApiModelProperty(value = "规格名称", required = true)
    @NotNull(message = "规格名称不能为空")
    private String appSkuName;

    /**
     * 是否面议[0否 1是]
     */
    @ApiModelProperty(value = "是否面议[0否 1是]", required = true)
    @NotNull(message = "是否面议不能为空")
    private String negotiable;

    /**
     * 服务周期
     */
    @ApiModelProperty(value = "服务周期", required = true)
    @NotNull(message = "服务周期不能为空")
    private String validity;

    /**
     * 服务周期单位[DAY,WEEK,MONTH,YEAR]
     */
    @ApiModelProperty(value = "服务周期单位[DAY,WEEK,MONTH,YEAR]")
    private String validityUnit;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格", required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
}
