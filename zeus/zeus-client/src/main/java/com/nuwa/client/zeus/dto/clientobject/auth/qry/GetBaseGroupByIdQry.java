package com.nuwa.client.zeus.dto.clientobject.auth.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * GetBaseGroupByIdQry 获取当个分组信息
 *
 * @author hy
 * @date 2021/5/25 15:32
 * @since 1.0.0
 */
@ApiModel(value = "查询当前用户拥有的权限和角色")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class GetBaseGroupByIdQry extends NuwaCommand {
    private Integer groupId;
}
