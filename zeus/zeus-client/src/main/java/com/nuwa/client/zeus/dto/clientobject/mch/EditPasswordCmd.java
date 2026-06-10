package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * CreateMerchantCmd 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审核失败-命令")
public class EditPasswordCmd extends NuwaCommand {

    @ApiModelProperty(value = "商户Id", required = true)
    @NotNull(message = "商户Id不能为空")
    private Integer mchId;

    @ApiModelProperty(value = "旧密码 不用加密", required = true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码 不用加密", required = true)
    private String newPassword;

}
