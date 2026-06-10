package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.client.zeus.dto.clientobject.mch.co.OpenAppCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MerchantOpenAppCmd 开通商户应用命令
 *
 * @author hy
 * @date 2021/6/3 9:35
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "重置密码-命令")
public class ResetMerchantPasswordCmd extends NuwaCommand {

    @ApiModelProperty(value = "商户Id", required = true)
    @NotNull(message = "商户Id不能为空")
    private Integer mchId;

}
