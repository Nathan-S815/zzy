package com.nuwa.app.ticket.command.query.order;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "退款订单分页查询")
public class MchRefundOrderPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("id")
    private Long orderNo;

    @ApiModelProperty("分销商-商户id")
    private Long mchId;

    @ApiModelProperty(value = "退款id", hidden = true)
    private Long refundId;

    @ApiModelProperty("退款流水号")
    private Long refundSerialNo;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("供应商产品编码")
    private String productCode;

    @ApiModelProperty("审核状态 待审核:1;审核拒绝:5;审核通过:10")
    private Integer auditStatus;

    @ApiModelProperty("状态 创建:1  退款中:2  已退款:3  退款失败:4")
    private Integer status;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("申请时间-开始")
    private Date applyDateStart;

    @ApiModelProperty("申请时间-结束")
    private Date applyDateEnd;

    @ApiModelProperty("游玩时间-开始")
    private Date visitDateStart;

    @ApiModelProperty("游玩时间-结束")
    private Date visitDateEnd;

}
