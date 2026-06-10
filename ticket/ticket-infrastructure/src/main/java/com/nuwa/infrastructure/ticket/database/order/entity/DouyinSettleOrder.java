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
 * 抖音结算订单表
 *
 * @author huyonghack@163.com
 * @since 2022-01-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DouyinSettleOrder对象")
public class DouyinSettleOrder extends Model<DouyinSettleOrder> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("结算订单号")
    private Long outOrderNo;

    @ApiModelProperty("渠道支付订单id")
    private Long channelPaymentId;

    @ApiModelProperty("渠道支付订单号")
    private Long channelPaymentOrderNo;

    @ApiModelProperty("抖音结算订单号")
    private String douyinSettleNo;

    @ApiModelProperty("结算金额")
    private BigDecimal settleAmount;

    @ApiModelProperty("状态 待结算:1 结算中:2 已结算:3  结算失败:4 已退款无需结算:5")
    private Integer status;

    @ApiModelProperty("结算执行时间")
    private Date timeExecuteSettle;

    @ApiModelProperty("结算时间")
    private Date timeSettle;

    @ApiModelProperty("扩展字段")
    private String extra;

    @ApiModelProperty("订单的错误码")
    private String failureCode;

    @ApiModelProperty("订单的错误消息的描述")
    private String failureMsg;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String OUT_ORDER_NO = "out_order_no";

    public static final String CHANNEL_PAYMENT_ID = "channel_payment_id";

    public static final String CHANNEL_PAYMENT_ORDER_NO = "channel_payment_order_no";

    public static final String DOUYIN_SETTLE_NO = "douyin_settle_no";

    public static final String SETTLE_AMOUNT = "settle_amount";

    public static final String STATUS = "status";

    public static final String TIME_EXECUTE_SETTLE = "time_execute_settle";

    public static final String TIME_SETTLE = "time_settle";

    public static final String EXTRA = "extra";

    public static final String FAILURE_CODE = "failure_code";

    public static final String FAILURE_MSG = "failure_msg";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
