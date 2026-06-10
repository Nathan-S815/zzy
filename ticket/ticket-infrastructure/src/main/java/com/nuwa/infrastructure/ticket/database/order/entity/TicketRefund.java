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
 * 退款表
 *
 * @author huyonghack@163.com
 * @since 2022-02-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TicketRefund对象")
public class TicketRefund extends Model<TicketRefund> {
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

    @ApiModelProperty("退款金额")
    private BigDecimal amount;

    @ApiModelProperty("实退金额")
    private BigDecimal realAmount;

    @ApiModelProperty("退款数量")
    private Integer quantity;

    @ApiModelProperty("实退数量")
    private Integer realQuantity;

    @ApiModelProperty("审核状态 待审核:1;审核拒绝:5;审核通过:10")
    private Integer auditStatus;

    @ApiModelProperty("状态 创建:1  退款中:2  已退款:3  退款失败:4")
    private Integer status;

    @ApiModelProperty("退款原因")
    private String refundReason;

    @ApiModelProperty("退款业务编码 C端用户申请:1;供应商支付失败,自动退款:2;异常订单退款处理:3;取消订单:4;供应商出票失败,退款处理:5")
    private Integer refundBizCode;

    @ApiModelProperty("退款时间")
    private Date timeRefund;

    @ApiModelProperty("退款审核时间")
    private Date timeAudit;

    @ApiModelProperty("退款拒绝原因")
    private String refundRejectReason;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("下单人ip")
    private String clientIp;

    @ApiModelProperty("扩展字段")
    private String extra;

    @ApiModelProperty("订单的错误码")
    private String failureCode;

    @ApiModelProperty("订单的错误消息的描述")
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

    public static final String AMOUNT = "amount";

    public static final String REAL_AMOUNT = "real_amount";

    public static final String QUANTITY = "quantity";

    public static final String REAL_QUANTITY = "real_quantity";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String STATUS = "status";

    public static final String REFUND_REASON = "refund_reason";

    public static final String REFUND_BIZ_CODE = "refund_biz_code";

    public static final String TIME_REFUND = "time_refund";

    public static final String TIME_AUDIT = "time_audit";

    public static final String REFUND_REJECT_REASON = "refund_reject_reason";

    public static final String USER_ID = "user_id";

    public static final String MCH_ID = "mch_id";

    public static final String SUPPLIER_MERCHANT_ID = "supplier_merchant_id";

    public static final String CLIENT_IP = "client_ip";

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
