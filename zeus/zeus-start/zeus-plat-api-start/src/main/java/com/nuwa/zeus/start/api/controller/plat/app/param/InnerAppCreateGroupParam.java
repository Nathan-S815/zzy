package com.nuwa.zeus.start.api.controller.plat.app.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * InnerAppCreateGroupParam 内部应用创建角色
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "内部应用创建角色")
public class InnerAppCreateGroupParam extends NuwaCommand {

    @ApiModelProperty(value = "应用id", required = true)
    @NotNull(message = "应用id不能为空")
    private Integer appId;

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "运营网站地址", required = true)
    @NotBlank(message = "运营网站地址")
    private String adminUrl;

    @ApiModelProperty(value = "备注")
    private String remark;

}
