package com.nuwa.client.zeus.dto.clientobject.app.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "应用商城Qry")
public class AppMallListQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "1:系统应用 2:功能应用")
    private Integer appType;

    @ApiModelProperty(value = "系统级应用 系统级应用传 -1")
    private Long parentAppId = -1L;

}
