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
import java.util.List;

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
@ApiModel(value = "创建商户子账号-命令")
public class CreateMerchantChildCmd extends NuwaCommand {

    @ApiModelProperty(value = "真实姓名", required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String name;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobilePhone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "分配应用")
    private List<Long> appIds;

    @ApiModelProperty(value = "ip",hidden = true)
    private String ip;

}
