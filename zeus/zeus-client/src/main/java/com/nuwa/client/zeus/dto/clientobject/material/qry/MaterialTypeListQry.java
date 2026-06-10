package com.nuwa.client.zeus.dto.clientobject.material.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资源分类Qry")
public class MaterialTypeListQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

}
