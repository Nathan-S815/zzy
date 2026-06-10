package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改商户应用登录提交url和景区编码-命令")
public class CreateMerchantAppSubmitUrlCmd extends NuwaCommand {

    @ApiModelProperty(value = "appId")
    private Long appId;

    @ApiModelProperty(value = "登录提交的url")
    private String loginSubmitUrl;

    @ApiModelProperty(value = "景区编码")
    private String scenicCode;
}
