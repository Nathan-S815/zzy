package com.nuwa.zeus.start.api.controller.plat.app.param;

import com.baomidou.mybatisplus.annotation.Version;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用创建版本")
public class CreateAppUpdateLogParam extends NuwaCommand {

    @ApiModelProperty("appId")
    private Long appId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("版本号")
    @Version
    private String version;

    @ApiModelProperty("版本内容")
    private String detail;
}
