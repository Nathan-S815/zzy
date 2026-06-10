package com.nuwa.client.zeus.dto.clientobject.base.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 平台升级日志 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "平台升级日志")
public class PlatUpgradeListQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

}
