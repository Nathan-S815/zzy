package com.nuwa.zeus.start.api.controller.common.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * MerchantRegisterCheckCodeParam
 *
 * @author hy
 * @date 2021/6/8 20:08
 * @since 1.0.0
 */
@Data
@ApiModel(value = "检测商户注册验证码")
public class MerchantRegisterCheckCodeParam {

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String code;
}
