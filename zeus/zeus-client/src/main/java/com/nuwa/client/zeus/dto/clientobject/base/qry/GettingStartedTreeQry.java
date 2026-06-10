package com.nuwa.client.zeus.dto.clientobject.base.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 新手入门 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新手入门")
public class GettingStartedTreeQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

}
