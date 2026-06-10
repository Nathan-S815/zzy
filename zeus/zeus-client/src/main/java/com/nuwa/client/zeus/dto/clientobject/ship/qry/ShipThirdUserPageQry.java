package com.nuwa.client.zeus.dto.clientobject.ship.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 跳转第三方商户用户信息表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "跳转第三方商户用户信息表PageQry")
public class ShipThirdUserPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

}
