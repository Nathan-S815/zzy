package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设置一码通端口功能
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SetClientScopeParam extends NuwaCommand {

    @ApiModelProperty("端口id")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "业务类型[多个逗号隔开] identity_code(身份码) verification_code(核销码)", example = "identity_code,verification_code")
    @NotBlank(message = "业务类型不能为空")
    private String bizList;
}
