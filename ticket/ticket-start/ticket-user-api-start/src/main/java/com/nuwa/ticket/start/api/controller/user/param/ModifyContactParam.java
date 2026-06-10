package com.nuwa.ticket.start.api.controller.user.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 修改联系人
 *
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyContactParam extends NuwaCommand {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("联系人姓名")
    @NotBlank
    private String name;

    @ApiModelProperty("联系人手机号")
    @NotBlank
    private String mobile;

    @ApiModelProperty("身份信息")
    @NotBlank
    private String idCard;

    @ApiModelProperty("是否默认 1:默认")
    private Integer defaultFlag;

    @ApiModelProperty("证件类型")
    private String certificateType;
}
