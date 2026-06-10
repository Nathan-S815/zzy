package com.nuwa.client.ticket.dto.clientobject.mall;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "创建订单命令")
public class UserPayMallTradeCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "订单ID 不能为空")
    private Long id;

    @NotNull(message = "支付宝appID 不能为空")
    private String appId;

    @NotNull(message = "buyerId 不能为空")
    private String buyerId;

    @NotNull(message = "app 类型 1:微信 2:支付宝")
    private String appType;

}
