package com.zzy.datawarehouse.display.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author nanHuang @南皇
 * @version 2022/9/13 15:36:07 nanHuang Exp $
 */
@Data
public class RegionParam {

    @ApiModelProperty(value = "父级区域ID，查询level为2,3时必填,level为1时无效(level=2,areaCode传的是省code)")
    private String areaCode;

    @NotEmpty(message = "level不能为空")
    @ApiModelProperty(value = "等级1省，2市，3区")
    private String level;
}
