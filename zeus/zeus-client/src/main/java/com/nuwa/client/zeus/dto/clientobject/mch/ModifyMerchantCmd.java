package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "修改商户-命令")
public class ModifyMerchantCmd extends NuwaCommand {

    private Long mchId;

//    @ApiModelProperty(value = "账号名", required = true)
//    @NotBlank(message = "账号名不能为空")
//    private String username;

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
    private String contentPhone;

}
