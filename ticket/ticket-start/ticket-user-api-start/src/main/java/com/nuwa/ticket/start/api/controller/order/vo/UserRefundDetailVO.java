package com.nuwa.ticket.start.api.controller.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.ticket.start.api.controller.dto.ProductRefundRuleConfigDTO;
import com.nuwa.ticket.start.api.controller.dto.ProductVerificationRuleConfigDTO;
import com.nuwa.ticket.start.api.controller.dto.TouristDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户订单详情
 *
 * @author hy
 */
@Data
public class UserRefundDetailVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("scenicspotId 景区id")
    private Long scenicspotId;

    @ApiModelProperty("productId 产品id")
    private Long productId;

    @ApiModelProperty("订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty("退款申请时间")
    private Date refundApplyTime;

    @ApiModelProperty("数量")
    private Integer refundQuantity;

    @ApiModelProperty("申请退款流水号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long refundSerialNo;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("退款失败原因")
    private String failureMsg;

    @ApiModelProperty("退款方式 1->原路退回")
    private Integer refundType;

    /**
     * 创建:1  退款中:2  已退款:3  退款失败:4
     */
    @ApiModelProperty("状态 (1,2->退款中) 3->退款成功 4->退款失败")
    private Integer refundStatus;

    @ApiModelProperty("状态描述 退款中  退款成功 退款失败")
    private String refundStatusName;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人身份证号码")
    private String linkIdCard;

    @ApiModelProperty("产品退款规则")
    private ProductRefundRuleConfigDTO refundRule;

    @ApiModelProperty("游玩人列表")
    private List<TouristDTO> touristList;
}
