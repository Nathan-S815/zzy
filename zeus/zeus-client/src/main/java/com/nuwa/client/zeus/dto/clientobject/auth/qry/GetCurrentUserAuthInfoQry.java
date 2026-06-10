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
@ApiModel(value = "查询当前用户拥有的权限和角色")
public class GetCurrentUserAuthInfoQry extends NuwaCommand {
    private Long userId;

    public GetCurrentUserAuthInfoQry(Long userId) {
        this.userId = userId;
    }
}
