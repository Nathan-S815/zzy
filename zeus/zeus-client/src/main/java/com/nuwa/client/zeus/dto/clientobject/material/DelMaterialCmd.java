package com.nuwa.client.zeus.dto.clientobject.material;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "删除图片资源命令")
public class DelMaterialCmd extends NuwaCommand {

    @ApiModelProperty(value = "图片ID,多个逗号分隔", required = true)
    @NotBlank(message = "ID不能为空")
    private String ids;
}
