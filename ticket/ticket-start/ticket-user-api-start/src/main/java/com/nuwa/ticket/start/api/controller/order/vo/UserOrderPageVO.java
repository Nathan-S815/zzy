package com.nuwa.ticket.start.api.controller.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单分页查询VO
 *
 * @author hy
 */
@Data
public class UserOrderPageVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("订单号")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("实付金额")
    private BigDecimal realAmount;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("是否有退款")
    private Boolean hasRefund;

    @ApiModelProperty("是否可退款")
    private Boolean canRefund;

    @ApiModelProperty("是否可取消")
    private Boolean canCancel;

    @ApiModelProperty("是否可支付")
    private Boolean canPay;

    @ApiModelProperty("状态 创建:0|待支付:1|待出票:2|已出票:3|已完成:4|已取消:5")
    private Integer status;

    @ApiModelProperty("产品类型,门票:1")
    private Integer productType;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    public static UserOrderPageVO toVO(TicketOrder order) {
        Integer status = order.getStatus();
        UserOrderPageVO vo = new UserOrderPageVO();
        BeanUtils.copyProperties(order, vo);
        vo.setHasRefund((order.getRefundingQuantity() + order.getRefundedQuantity()) > 0);
        vo.setProductType(1);
        vo.setCanCancel(status.equals(TicketOrderEnum.created.getCode()) || status.equals(TicketOrderEnum.paying.getCode()));
        vo.setCanPay(status.equals(TicketOrderEnum.created.getCode()) || status.equals(TicketOrderEnum.paying.getCode()));
        if (status.equals(TicketOrderEnum.ticketed.getCode())) {
            vo.setCanRefund(order.getRefundedQuantity() + order.getRefundingQuantity() + order.getAlreadyConsumeQuantity() < order.getQuantity());
        } else {
            vo.setCanRefund(false);
        }
        return vo;
    }
}
