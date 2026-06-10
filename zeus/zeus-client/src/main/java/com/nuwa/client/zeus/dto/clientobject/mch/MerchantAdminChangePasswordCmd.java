package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
 * <pre>
 * 获取商户已开通应用列表
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改密码")
public class MerchantAdminChangePasswordCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旧密码", required = true)
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
