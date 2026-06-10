package com.nuwa.client.zeus.dto.clientobject.app;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AppAddMenuCmd 添加应用菜单
 *
 * @author hy
 * @date 2021/5/31 13:33
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "微页面修改-命令")
public class ModifyAppPageInfoCmd extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String pageName;

//    @ApiModelProperty(value = "名称", required = true)
//    @NotBlank(message = "名称不能为空")
//    private String pageDescribe;

    @ApiModelProperty(value = "页面地址", required = true)
    @NotBlank(message = "页面地址不能为空")
    private String pageUri;
}
