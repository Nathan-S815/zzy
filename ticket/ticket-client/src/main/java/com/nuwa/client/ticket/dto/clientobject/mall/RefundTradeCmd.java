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
@ApiModel(value = "退款申请命令")
public class RefundTradeCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "退款理由 不能为空")
    private String refundReason;

    private Long id;

    private Long appId;

}
