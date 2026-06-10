package com.nuwa.client.ticket.dto.clientobject.mall.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 字典数据表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "字典数据表PageQry")
public class SysDictDataPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    private String name;

}
