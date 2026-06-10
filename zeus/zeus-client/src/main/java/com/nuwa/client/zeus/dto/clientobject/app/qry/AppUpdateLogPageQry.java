package com.nuwa.client.zeus.dto.clientobject.app.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * app升级日志 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-06-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "app升级日志PageQry")
public class AppUpdateLogPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("appId")
    private Long appId;

    @ApiModelProperty("名称")
    private String title;
}
