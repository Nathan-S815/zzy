package com.nuwa.infrastructure.ticket.database.order.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 供应商退款订单表
 *
 * @author huyonghack@163.com
 * @since 2021-12-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SupplierRefundOrder对象")
public class SupplierRefundOrder extends Model<SupplierRefundOrder> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("退款流水号")
    private Long refundSerialNo;

    @ApiModelProperty("供应商支付订单id")
    private Long supplierPaymentOrderId;

    @ApiModelProperty("平台退款订单id")
    private Long ticketRefundId;

    @ApiModelProperty("供应商退款订单号")
    private Long supplierRefundOrderNo;

    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("退款金额")
    private BigDecimal amount;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("审核状态 待审核:1;审核拒绝:5;审核通过:10")
    private Integer auditStatus;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("推送状态 待推送:0;推送中:1;推送失败:2;推送成功:10")
    private Integer pushStatus;

    @ApiModelProperty("重试次数")
    private Integer retryTimes;

    @ApiModelProperty("下次重试时间")
    private Date timeNext;

    @ApiModelProperty("最后重试时间")
    private Date timeLastRetry;

    @ApiModelProperty("退款时间")
    private Date timePaid;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("扩展字段")
    private String extra;

    @ApiModelProperty("错误码")
    private String failureCode;

    @ApiModelProperty("错误消息的描述")
    private String failureMsg;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String REFUND_SERIAL_NO = "refund_serial_no";

    public static final String SUPPLIER_PAYMENT_ORDER_ID = "supplier_payment_order_id";

    public static final String TICKET_REFUND_ID = "ticket_refund_id";

    public static final String SUPPLIER_REFUND_ORDER_NO = "supplier_refund_order_no";

    public static final String SUPPLIER_ID = "supplier_id";

    public static final String AMOUNT = "amount";

    public static final String QUANTITY = "quantity";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String REFUND_REASON = "refund_reason";

    public static final String PUSH_STATUS = "push_status";

    public static final String RETRY_TIMES = "retry_times";

    public static final String TIME_NEXT = "time_next";

    public static final String TIME_LAST_RETRY = "time_last_retry";

    public static final String TIME_PAID = "time_paid";

    public static final String USER_ID = "user_id";

    public static final String EXTRA = "extra";

    public static final String FAILURE_CODE = "failure_code";

    public static final String FAILURE_MSG = "failure_msg";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
