package com.nuwa.ticket.start.api.controller.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.enums.TicketOrderEnum;
import com.nuwa.ticket.start.api.controller.dto.ProductRefundRuleConfigDTO;
import com.nuwa.ticket.start.api.controller.dto.ProductValidPeriodConfigDTO;
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
public class UserOrderDetailVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("订单号")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("scenicspotId 景区id")
    private Long scenicspotId;

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

    @ApiModelProperty("服务电话")
    private String servicePhone;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人身份证号码")
    private String linkIdCard;

    @ApiModelProperty("已核销数量")
    private Integer alreadyConsumeQuantity;

    @ApiModelProperty("可核销数量")
    private Integer availableConsumeQuantity;

    @ApiModelProperty("退款中数量")
    private Integer refundingQuantity;

    @ApiModelProperty("已退数量")
    private Integer refundedQuantity;

    @ApiModelProperty("开放时间")
    private String openTime;

    @ApiModelProperty("订单失效时间的 Unix 时间戳")
    private Date timeExpire;

    @ApiModelProperty("场次开始时间")
    private String sessionStartTime;

    @ApiModelProperty("场次结束时间")
    private String sessionEndTime;

    @ApiModelProperty("产品核销规则")
    private ProductVerificationRuleConfigDTO verificationRule;

    @ApiModelProperty("产品退款规则")
    private ProductRefundRuleConfigDTO refundRule;

    @ApiModelProperty("游玩人列表")
    private List<TouristDTO> touristList;

    public static UserOrderDetailVO toVO(TicketOrder order) {
        Integer status = order.getStatus();
        UserOrderDetailVO vo = new UserOrderDetailVO();
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
