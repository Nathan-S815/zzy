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
 * CreateMerchantCmd 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建商户-命令")
public class CreateMerchantCmd extends NuwaCommand {

    @ApiModelProperty(value = "账号名", required = true)
    @NotBlank(message = "账号名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码名不能为空")
    private String password;

    @ApiModelProperty(value = "账号类型", example = "账号类型 1:个人,2企业,3:政府组织", required = true)
    @NotNull(message = "账号类型不能为空")
    private Integer mchType;

    @ApiModelProperty(value = "商户名称", required = true)
    @NotBlank(message = "商户名称不能为空")
    private String mchName;

    @ApiModelProperty(value = "联系人姓名")
    private String contentName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "所在地省")
    private String province;

    @ApiModelProperty(value = "所在市")
    private String city;

    @ApiModelProperty(value = "所在地区")
    private String county;

    @ApiModelProperty(value = "具体地址")
    private String address;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String contentPhone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus = 1;

    @ApiModelProperty(value = "logo")
    private Long logo;

}
