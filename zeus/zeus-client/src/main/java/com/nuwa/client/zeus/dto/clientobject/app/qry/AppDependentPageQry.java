package com.nuwa.client.zeus.dto.clientobject.app.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 应用依耐表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用依耐表PageQry")
public class AppDependentPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
