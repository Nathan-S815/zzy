package com.nuwa.infrastructure.ticket.database.alipaydata.entity;

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
 * 支付宝-景区订单
 *
 * @author huyonghack@163.com
 * @since 2022-05-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlipayDataOrder对象")
public class AlipayDataOrder extends Model<AlipayDataOrder> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("景区小程序id")
    private String alipayAppId;

    @ApiModelProperty("下单用户")
    private String buyerId;

    @ApiModelProperty("服务商的标识")
    private String sourceSystem;

    @ApiModelProperty("订单外部id")
    private String outerOrderId;

    @ApiModelProperty("景区id")
    private String outerScenicId;

    @ApiModelProperty("下单时间")
    private Date orderCreateTime;

    @ApiModelProperty("更新时间")
    private Date orderModifiedTime;

    @ApiModelProperty("金额")
    private String amount;

    @ApiModelProperty("产品id")
    private Integer productId;

    @ApiModelProperty("订单状态\n" +
            "CHECK_WAITING(\"CHECK_WAITING\",待检票)，\n" +
            "CHECKED(\"CHECKED\",已检票)，\n" +
            "FINISHED(\"FINISHED\",已完结)，\n" +
            "TICKET_RUNNING(\"TICKET_RUNNING\",出票中)，\n" +
            "PAY_WAITING(\"PAY_WAITING\",待付款)，\n" +
            "REFUND_AUDITING(\"REFUND_AUDITING\",退单审核中)，\n" +
            "REFUND_SUCCESS(\"REFUND_SUCCESS\",已退单)，\n" +
            "CLOSED(\"CLOSED\",已关闭),\n" +
            "REFUND_RUNNING(\"REFUND_RUNNING\",\"退单中\")")
    private String orderStatus;

    @ApiModelProperty("订单详情页链接，必须是alipay://开头的小程序scheme,page参数值必须做URL ENCODE")
    private String orderLink;

    @ApiModelProperty("订单类型\n" +
            "TICKET(\"TICKET\",\"门票订单\")")
    private String orderType;

    @ApiModelProperty("购票来源")
    private String source;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("门票信息（\n" +
            "ticket_no：【单据号】\n" +
            "ticket_type【门票类型\n" +
            "NORMAL(\"NORMAL\",\"普通\"),\n" +
            "GROUP(\"GROUP\",\"套票\"),\n" +
            "PERIOD(\"PERIOD\",\"时段票\"),\n" +
            "REGION(\"REGION\",\"区域票\")】，ticket_specs【门票规格\n" +
            "成人票、全价票】，use_start_date（使用开始日期），use_end_date：【使用结束日期\t】，status【\n" +
            "\t门票状态\n" +
            "INIT(\"INIT\",\"初始化\")\n" +
            "TICKET_RUNNING(\"TICKET_RUNNING\",出票中)\n" +
            "TICKET_SUCCESS(TICKET_SUCCESS,出票成功)\n" +
            "TICKET_FAILURE(\"TICKET_FAILURE\",出票失败)\n" +
            "TO_USE(\"TO_USE\",待核销)\n" +
            "USED(\"USED\",已核销)\n" +
            "CLOSED(\"CLOSED\",已完结)\n" +
            "REFUND_RUNNING(\"REFUND_RUNNING\",待退票)\n" +
            "REFUND_SUCCESS(\"REFUND_SUCCESS\",已退票)\n" +
            "REFUND_FAILURE(\"REFUND_FAILURE\",退票失败)\n" +
            "】，ticket_count，price：【门票单价】）")
    private String ticketJson;

    @ApiModelProperty("当前版本号")
    @Version
    private Long version;

    @ApiModelProperty("最新版本号")
    private Long lastVersion;

    @ApiModelProperty("最近更新时间")
    private Date lastUpdateTime;

    @ApiModelProperty("支付宝订单id")
    private String alipayOrderId;


    public static final String ID = "id";

    public static final String ALIPAY_APP_ID = "alipay_app_id";

    public static final String BUYER_ID = "buyer_id";

    public static final String SOURCE_SYSTEM = "source_system";

    public static final String OUTER_ORDER_ID = "outer_order_id";

    public static final String OUTER_SCENIC_ID = "outer_scenic_id";

    public static final String ORDER_CREATE_TIME = "order_create_time";

    public static final String ORDER_MODIFIED_TIME = "order_modified_time";

    public static final String AMOUNT = "amount";

    public static final String PRODUCT_ID = "product_id";

    public static final String ORDER_STATUS = "order_status";

    public static final String ORDER_LINK = "order_link";

    public static final String ORDER_TYPE = "order_type";

    public static final String SOURCE = "source";

    public static final String TICKET_JSON = "ticket_json";

    public static final String VERSION = "version";

    public static final String LAST_VERSION = "last_version";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String ALIPAY_ORDER_ID = "alipay_order_id";

}
