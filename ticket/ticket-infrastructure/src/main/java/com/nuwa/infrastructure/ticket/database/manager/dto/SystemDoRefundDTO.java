package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 系统退款处理
 *
 * @author hy
 */
@Data
public class SystemDoRefundDTO {
    private UserAware userAware;

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    private String reason;

    @ApiModelProperty("退款业务编码 C端用户申请:1;供应商支付失败,自动退款:2;异常订单退款处理:3;供应商主动退款:4;供应商出票失败,退款处理:5,供应商取消订单,退款处理:6")
    private Integer refundBizCode;

    @ApiModelProperty("审核状态 待审核:1;审核拒绝:5;审核通过:10")
    private Integer auditStatus;
}
