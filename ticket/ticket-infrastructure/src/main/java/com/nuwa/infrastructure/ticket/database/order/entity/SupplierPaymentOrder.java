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
 * 供应商支付订单表
 *
 * @author huyonghack@163.com
 * @since 2021-12-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SupplierPaymentOrder对象")
public class SupplierPaymentOrder extends Model<SupplierPaymentOrder> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("支付流水号")
    private Long paymentNo;

    @ApiModelProperty("供应商支付订单号")
    private Long supplierOrderNo;

    @ApiModelProperty("商户供应商id")
    private String mchSupplierId;

    @ApiModelProperty("供应商类型")
    private String supplierType;

    @ApiModelProperty("供应商商户号")
    private String supplierMch;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    @ApiModelProperty("状态 待下单:0;下单失败:1;待支付:2;支付失败:3;出票中:4;出票失败:5;出票成功:10;分销商取消:20;供应商取消:30")
    private Integer status;

    @ApiModelProperty("推送状态 开启:1 停止:0")
    private Integer pushStatus;

    @ApiModelProperty("重试次数")
    private Integer retryTimes;

    @ApiModelProperty("下次重试时间")
    private Date timeNext;

    @ApiModelProperty("最后重试时间")
    private Date timeLastRetry;

    @ApiModelProperty("支付时间")
    private Date timePaid;

    @ApiModelProperty("出票时间")
    private Date timeTicket;

    @ApiModelProperty("用户id")
    private Long userId;

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

    public static final String PAYMENT_NO = "payment_no";

    public static final String SUPPLIER_ORDER_NO = "supplier_order_no";

    public static final String MCH_SUPPLIER_ID = "mch_supplier_id";

    public static final String SUPPLIER_TYPE = "supplier_type";

    public static final String SUPPLIER_MCH = "supplier_mch";

    public static final String SUPPLIER_PRODUCT_CODE = "supplier_product_code";

    public static final String AMOUNT = "amount";

    public static final String STATUS = "status";

    public static final String PUSH_STATUS = "push_status";

    public static final String RETRY_TIMES = "retry_times";

    public static final String TIME_NEXT = "time_next";

    public static final String TIME_LAST_RETRY = "time_last_retry";

    public static final String TIME_PAID = "time_paid";

    public static final String TIME_TICKET = "time_ticket";

    public static final String USER_ID = "user_id";

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
