package com.nuwa.client.zeus.dto.clientobject.other.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * app试用申请 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-07-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "app试用申请PageQry")
public class AppApplyPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
