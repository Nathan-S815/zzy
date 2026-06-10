package com.nuwa.app.ticket.command.query.order;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
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
@ApiModel(value = "上游分销商订单分页查询")
public class SupplierOrderPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("供应商商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("平台订单号")
    private Long orderNo;

    @ApiModelProperty("平台流水号")
    private Long paymentNo;

/*    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("供应商id")
    private Long supplierId;*/

    @ApiModelProperty("游玩日期-开始")
    private Date visitDateStart;

    @ApiModelProperty("游玩日期-结束")
    private Date visitDateEnd;

    @ApiModelProperty("下单日期-开始")
    private Date createDateStart;

    @ApiModelProperty("下单日期-结束")
    private Date createDateEnd;

/*    @ApiModelProperty("供应商订单状态")
    private Integer supplierOrderStatus;*/

}
