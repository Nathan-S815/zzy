package com.nuwa.zeus.start.api.controller.merchant.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改域名和备案号")
public class ModifyDomainAndApproveNoParam extends NuwaCommand {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id")
    private Long id;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("备案号")
    private String websiteApproveNo;

}
