package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 交易详情
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallPaymentRefund extends Model<MallPaymentRefund> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布人
     */
    private Long mchId;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 商户省
     */
    private String provinceId;

    /**
     * 商户市
     */
    private String cityId;

    /**
     * 商户区
     */
    private String countyId;

    /**
     * 创建用户
     */
    private String createBy;

    /**
     * 更新用户
     */
    private String updateBy;

    /**
     * 交易订单号
     */
    private String tradeNo;

    /**
     * 退款订单号
     */
    private String paymentOrderNo;

    /**
     * 退款用户ID
     */
    private Long memberId;

    /**
     * 支付平台订单号
     */
    private String bankSerialNo;

    /**
     * 退款金额(单位分)
     */
    private Long refundAmount;

    /**
     * 成功退款时间
     */
    private Date paySuccessTime;

    /**
     * 退款状态(10审核中 11退款中 12已退款 13审核失败 14退款失败)
     */
    private Integer tradeStatus;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志 0正常1删除
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY_ID = "city_id";

    public static final String COUNTY_ID = "county_id";

    public static final String CREATE_BY = "create_by";

    public static final String UPDATE_BY = "update_by";

    public static final String TRADE_NO = "trade_no";

    public static final String PAYMENT_ORDER_NO = "payment_order_no";

    public static final String MEMBER_ID = "member_id";

    public static final String BANK_SERIAL_NO = "bank_serial_no";

    public static final String REFUND_AMOUNT = "refund_amount";

    public static final String PAY_SUCCESS_TIME = "pay_success_time";

    public static final String TRADE_STATUS = "trade_status";

    public static final String CANCEL_TIME = "cancel_time";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
