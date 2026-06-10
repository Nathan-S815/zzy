package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


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
public class MerchantChangePasswordCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标识码", required = true)
    @NotBlank(message = "标识码不能为空")
    private String code;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
