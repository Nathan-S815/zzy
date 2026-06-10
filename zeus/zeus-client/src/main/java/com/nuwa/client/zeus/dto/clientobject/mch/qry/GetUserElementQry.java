package com.nuwa.client.zeus.dto.clientobject.mch.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * <pre>
 * 获取商户已开通应用列表
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "获取按钮树")
public class GetUserElementQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "appId", required = true)
    @NotNull(message = "appId不能为空")
    private Long id;


    @ApiModelProperty(value = "parentId", required = true)
    @NotNull(message = "parentId不能为空")
    private Long appId;
}
