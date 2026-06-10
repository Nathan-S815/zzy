package com.nuwa.ticket.start.api.controller.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
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
public class MchOrderPageVO {
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("退款中数量")
    private Integer refundingQuantity;

    @ApiModelProperty("已退金额")
    private BigDecimal refundedAmount;

    @ApiModelProperty("已退数量")
    private Integer refundedQuantity;

    @ApiModelProperty("已核销数量")
    private Integer alreadyConsumeQuantity;

    @ApiModelProperty("可核销数量")
    private Integer availableConsumeQuantity;

    @ApiModelProperty("状态 创建:0;待支付:1;待出票:2;已出票:3;已完成:4;已取消:5")
    private Integer status;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("产品类型")
    private Integer productType;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("邀请码")
    private String promoterCode;

    @ApiModelProperty("b2b供应商id")
    private Long supplierId;

    @ApiModelProperty("供应商id")
    private Long supplierMerchantId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMerchantId;

    @ApiModelProperty("分销商-商户名称")
    private String distributeName;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("订单的错误码")
    private String failureCode;

    @ApiModelProperty("订单的错误消息的描述")
    private String failureMsg;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public static MchOrderPageVO toVO(TicketOrder order) {
        Integer status = order.getStatus();
        MchOrderPageVO vo = new MchOrderPageVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }
}
