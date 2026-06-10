package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "校验验证码")
public class MerchantRegisterCheckCodeCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;

}
