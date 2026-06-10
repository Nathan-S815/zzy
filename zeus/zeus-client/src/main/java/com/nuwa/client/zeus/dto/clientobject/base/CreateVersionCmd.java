package com.nuwa.client.zeus.dto.clientobject.base;

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
 * CreateBaseGroupCmd 创建分组
 *
 * @author hy
 * @date 2021/5/25 17:13
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建版本-命令")
public class CreateVersionCmd extends NuwaCommand {

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "版本号", required = true)
    @NotBlank(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "升级时间", required = true)
    @NotBlank(message = "升级时间不能为空")
    private String upgradeDate;

    @ApiModelProperty(value = "正文内容", required = true)
    @NotNull(message = "正文内容不能为空")
    private List<String> items;

}
