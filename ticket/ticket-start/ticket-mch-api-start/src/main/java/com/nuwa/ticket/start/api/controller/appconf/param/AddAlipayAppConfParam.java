package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AddAlipayAppConfParam 添加支付宝模板小程序客户端")
public class AddAlipayAppConfParam extends NuwaCommand {
    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("模板三方应用AppId")
    private String thirdAppId;

    @ApiModelProperty("小程序模板id")
    private String appTemplateId;

    @ApiModelProperty("商户clientId")
    private Long mchClientId;

    @ApiModelProperty("应用类型alipay_mini_template")
    private String appType;

    @NotBlank(message = "类型不能为空")
    @ApiModelProperty("类型 SINGLE_SCENIC:单景点 PLATE:全域")
    private String type;

    @ApiModelProperty("poi id")
    private Long poiId;

    @ApiModelProperty("poiList 多个用逗号(,)隔开")
    private String poiList;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("所属省份id")
    private Long provinceId;

    @ApiModelProperty("所属省份名称")
    private String provinceName;

    @ApiModelProperty("所属地市id")
    private Long cityId;

    @ApiModelProperty("所属地市名称")
    private String cityName;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("渠道公钥")
    @NotBlank(message = "渠道公钥不能为空")
    private String channelPublicKey;

    @ApiModelProperty("商户私钥")
    @NotBlank(message = "商户私钥不能为空")
    private String mchPrivateKey;

    @ApiModelProperty("商户授权码")
    @NotBlank(message = "商户授权码不能为空")
    private String appAuthCode;
}
