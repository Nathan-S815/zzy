package com.nuwa.ticket.start.api.controller.order.vo;

import com.nuwa.ticket.start.api.controller.dto.ProductRefundRuleConfigDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户退款确认
 *
 * @author hy
 */
@Data
public class UserRefundConfirmVO {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("产品类型")
    private Integer productType;

    @ApiModelProperty("可退数量")
    private Integer availableRefundQuantity;

    @ApiModelProperty("可退金额")
    private BigDecimal availableRefundAmount;

    @ApiModelProperty("退款规则")
    private ProductRefundRuleConfigDTO refundRuleConfig;

    @ApiModelProperty("退款原因")
    private List<String> reasonList;
}
