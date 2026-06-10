package com.nuwa.client.zeus.dto.clientobject.app.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 应用信息 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用信息PageQry")
public class AppInfoPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用类型 1:独立应用 2:功能应用")
    private Integer appType;

    @ApiModelProperty(value = "上架状态 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty(value = "应用提供方", example = "outer |  inner")
    private String provider;

}
