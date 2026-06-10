package com.nuwa.client.zeus.dto.clientobject.auth.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * GetCurrentUserAuthInfoQry
 *
 * @author hy
 * @date 2021/5/25 15:32
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询当前用户信息")
public class GetCurrentUserInfoQry extends NuwaCommand {

}
