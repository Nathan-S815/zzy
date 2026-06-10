package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户应用授权服务周期参数")
public class AuthServerTimeParam extends NuwaCommand {

    private Long mchId;

    @ApiModelProperty("应用id")
    private Long appId;

    @ApiModelProperty("父应用")
    private Long parentAppId;

    @ApiModelProperty("服务周期开始时间")
    private Date startTime;

    @ApiModelProperty("服务周期结束时间")
    private Date endTime;
}
