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
 * 渠道支付订单表
 *
 * @author huyonghack@163.com
 * @since 2021-12-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ChannelPaymentOrder对象")
public class ChannelPaymentOrder extends Model<ChannelPaymentOrder> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("商户支付订单号")
    private Long mchPayOrderNo;

    @ApiModelProperty("渠道支付订单号")
    private Long channelPayOrderNo;

    @ApiModelProperty("支付配置id")
    private Long payConfigId;

    @ApiModelProperty("支付商户号")
    private String channelMchNo;

    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    @ApiModelProperty("状态 创建:0  待支付:1  已支付:2  已关闭:3  失败:4")
    private Integer status;

    @ApiModelProperty("支付时间")
    private Date timePaid;

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

    @ApiModelProperty("支付渠道")
    private String payChannel;

    @ApiModelProperty("支付方式")
    private String payType;

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

    public static final String MCH_PAY_ORDER_NO = "mch_pay_order_no";

    public static final String CHANNEL_PAY_ORDER_NO = "channel_pay_order_no";

    public static final String PAY_CONFIG_ID = "pay_config_id";

    public static final String CHANNEL_MCH_NO = "channel_mch_no";

    public static final String AMOUNT = "amount";

    public static final String STATUS = "status";

    public static final String TIME_PAID = "time_paid";

    public static final String USER_ID = "user_id";

    public static final String CLIENT_IP = "client_ip";

    public static final String EXTRA = "extra";

    public static final String FAILURE_CODE = "failure_code";

    public static final String FAILURE_MSG = "failure_msg";

    public static final String PAY_CHANNEL = "pay_channel";

    public static final String PAY_TYPE = "pay_type";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
