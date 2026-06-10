package com.nuwa.client.ticket.dto.clientobject.mall;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "退款命令")
public class RefundApplyCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易ID", required = true)
    private Long id;

    @ApiModelProperty(value = "退款金额", required = true)
    private Long refundAmount;

    private Long appId;

}
