package com.nuwa.ticket.start.api.controller.appconf.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SetPaymentConfParam 设置参数")
public class SetPaymentConfParam extends NuwaCommand {

    @ApiModelProperty("支付密钥")
    private String salt;

    @ApiModelProperty("渠道商户号")
    private String channelMchId;
}
