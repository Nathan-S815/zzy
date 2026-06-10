package com.nuwa.client.discovery.dto.clientobject.user.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 达人账号绑定表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人账号绑定表PageQry")
public class MemberAccuntBindPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，主键，自动增长")
    private Integer userId;

}
