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
 * 订单表
 *
 * @author huyonghack@163.com
 * @since 2022-04-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TicketOrder对象")
public class TicketOrder extends Model<TicketOrder> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("实付金额")
    private BigDecimal realAmount;

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

    @ApiModelProperty("状态 创建:0 待支付:1 待出票:2 已出票:3 已完成:4 已取消:5")
    private Integer status;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("场次id")
    private Long sessionId;

    @ApiModelProperty("产品类型")
    private Integer productType;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("供应商渠道类型")
    private Integer supplierChannelType;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("游玩日期")
    private Date visitDate;

    @ApiModelProperty("联系人身份证号码")
    private String linkIdCard;

    @ApiModelProperty("支付时间")
    private Date timePaid;

    @ApiModelProperty("订单失效时间的 Unix 时间戳")
    private Date timeExpire;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("来源客户端 douyin_mini")
    private String clientSrc;

    @ApiModelProperty("支付渠道订单id")
    private Long channelPaymentOrderId;

    @ApiModelProperty("推广人标识")
    private String promoterCode;

    @ApiModelProperty("推广人用户id")
    private Long promoterUserId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("下单人ip")
    private String clientIp;

    @ApiModelProperty("来源appName")
    private String srcAppName;

    @ApiModelProperty("来源appId")
    private Long srcAppId;

    @ApiModelProperty("扩展字段")
    private String extra;

    @ApiModelProperty("订单的错误码")
    private String failureCode;

    @ApiModelProperty("订单的错误消息的描述")
    private String failureMsg;

    @ApiModelProperty("来源渠道 self")
    private String channelSrc;

    @ApiModelProperty("快照数据版本号")
    private Integer snapshootVersion;

    @ApiModelProperty("抖音渠道结算状态 1:待处理  2:已处理")
    private Integer douyinSettleStatus;

    private String lastUpdateByName;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    private Date lastUpdateTime;

    private String lastUpdateById;

    private String createById;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;


    public static final String ID = "id";

    public static final String ORDER_NO = "order_no";

    public static final String AMOUNT = "amount";

    public static final String REAL_AMOUNT = "real_amount";

    public static final String UNIT_PRICE = "unit_price";

    public static final String QUANTITY = "quantity";

    public static final String REFUNDING_QUANTITY = "refunding_quantity";

    public static final String REFUNDED_AMOUNT = "refunded_amount";

    public static final String REFUNDED_QUANTITY = "refunded_quantity";

    public static final String ALREADY_CONSUME_QUANTITY = "already_consume_quantity";

    public static final String AVAILABLE_CONSUME_QUANTITY = "available_consume_quantity";

    public static final String STATUS = "status";

    public static final String PRODUCT_NAME = "product_name";

    public static final String PRODUCT_ID = "product_id";

    public static final String SESSION_ID = "session_id";

    public static final String PRODUCT_TYPE = "product_type";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String SUPPLIER_CHANNEL_TYPE = "supplier_channel_type";

    public static final String SUPPLIER_ID = "supplier_id";

    public static final String SUPPLIER_PRODUCT_CODE = "supplier_product_code";

    public static final String VISIT_DATE = "visit_date";

    public static final String LINK_ID_CARD = "link_id_card";

    public static final String TIME_PAID = "time_paid";

    public static final String TIME_EXPIRE = "time_expire";

    public static final String LINK_MOBILE = "link_mobile";

    public static final String LINK_NAME = "link_name";

    public static final String CLIENT_SRC = "client_src";

    public static final String CHANNEL_PAYMENT_ORDER_ID = "channel_payment_order_id";

    public static final String PROMOTER_CODE = "promoter_code";

    public static final String PROMOTER_USER_ID = "promoter_user_id";

    public static final String USER_ID = "user_id";

    public static final String MCH_ID = "mch_id";

    public static final String SUPPLIER_MERCHANT_ID = "supplier_merchant_id";

    public static final String CLIENT_IP = "client_ip";

    public static final String SRC_APP_NAME = "src_app_name";

    public static final String SRC_APP_ID = "src_app_id";

    public static final String EXTRA = "extra";

    public static final String FAILURE_CODE = "failure_code";

    public static final String FAILURE_MSG = "failure_msg";

    public static final String CHANNEL_SRC = "channel_src";

    public static final String SNAPSHOOT_VERSION = "snapshoot_version";

    public static final String DOUYIN_SETTLE_STATUS = "douyin_settle_status";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

}
