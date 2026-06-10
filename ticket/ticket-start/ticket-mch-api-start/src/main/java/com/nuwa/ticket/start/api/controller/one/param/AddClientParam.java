package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增一码通端口
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddClientParam extends NuwaCommand {
    @ApiModelProperty("小程序AppId")
    @NotNull(message = "appid不能为空")
    private String outAppId;

    @ApiModelProperty("应用类型 weixin_mp(公众号)")
    @NotBlank(message = "应用类型不能为空")
    private String appType;

    @ApiModelProperty(value = "业务类型[多个逗号隔开] identity_code(身份码) verification_code(核销码)", example = "identity_code,verification_code")
    @NotBlank(message = "业务类型不能为空")
    private String bizList;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("背景图片id")
    private String backgroundPictureId;

    @ApiModelProperty("背景颜色")
    private String backgroundColor;

    @ApiModelProperty("备注信息")
    private String remark;
}
