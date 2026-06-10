package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallTrade extends Model<MallTrade> {
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
     * 支付订单号
     */
    private String orderNo;

    /**
     * 退款流水号
     */
    private String refundSerialNo;

    /**
     * 交易订单号
     */
    private String tradeNo;

    /**
     * 平台商户订单号
     */
    private String mchOrderNo;

    /**
     * 下单用户ID
     */
    private Long memberId;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 一级分类ID
     */
    private Long classificationFirstId;

    /**
     * 规格ID
     */
    private Long specificationsId;

    /**
     * 购买数量
     */
    private Integer productNum;

    /**
     * 收获方式(10线上发货 11线下门店取货)
     */
    private Integer receivingMethod;

    /**
     * 门店地址
     */
    private String storeAddress;

    /**
     * 门店经度
     */
    private String longitude;

    /**
     * 门店维度
     */
    private String latitude;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
     */
    private String consigneeTel;

    /**
     * 收货人地址
     */
    private String consigneeAddr;

    /**
     * 物流信息ID
     */
    private Long expressId;

    /**
     * 应付金额(单位分)
     */
    @JsonSerialize(using = PriceJson.class)
    private Long totalAmount;

    /**
     * 实付金额(单位分)
     */
    @JsonSerialize(using = PriceJson.class)
    private Long payAmount;

    /**
     * 成功支付时间
     */
    private Date paySuccessTime;

    /**
     * 支付状态(10待支付 20已支付 30已取消 40已过期)
     */
    private Integer payStatus;

    /**
     * 支付通道(100微信,101支付宝)
     */
    private Integer payChannel;

    /**
     * 订单类型(10直销)
     */
    private Integer orderType;

    /**
     * 支付方式(10在线支付)
     */
    private Integer payType;

    /**
     * 订单状态(10待支付 11代发货 12待收货 13已完成 14退款审核 15退款中 16已取消 17已退款 18退款失败)
     */
    private Integer orderStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付有效期
     */
    private Date expireTime;

    /**
     * 订单完成时间
     */
    private Date finishTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 退款理由
     */
    private String refundReason;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 退款金额(单位分)
     */
    private Long refundAmount;

    /**
     * 退款流水号
     */
    private String refundOrderNo;

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

    public static final String ORDER_NO = "order_no";

    public static final String TRADE_NO = "trade_no";

    public static final String MCH_ORDER_NO = "mch_order_no";

    public static final String MEMBER_ID = "member_id";

    public static final String PAY_ACCOUNT = "pay_account";

    public static final String PRODUCT_ID = "product_id";

    public static final String PRODUCT_NAME = "product_name";

    public static final String SPECIFICATIONS_ID = "specifications_id";

    public static final String PRODUCT_NUM = "product_num";

    public static final String RECEIVING_METHOD = "receiving_method";

    public static final String STORE_ADDRESS = "store_address";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String CONSIGNEE_NAME = "consignee_name";

    public static final String CONSIGNEE_TEL = "consignee_tel";

    public static final String CONSIGNEE_ADDR = "consignee_addr";

    public static final String EXPRESS_ID = "express_id";

    public static final String TOTAL_AMOUNT = "total_amount";

    public static final String PAY_AMOUNT = "pay_amount";

    public static final String PAY_SUCCESS_TIME = "pay_success_time";

    public static final String PAY_STATUS = "pay_status";

    public static final String PAY_CHANNEL = "pay_channel";

    public static final String ORDER_TYPE = "order_type";

    public static final String PAY_TYPE = "pay_type";

    public static final String ORDER_STATUS = "order_status";

    public static final String REMARK = "remark";

    public static final String EXPIRE_TIME = "expire_time";

    public static final String FINISH_TIME = "finish_time";

    public static final String CANCEL_TIME = "cancel_time";

    public static final String REFUND_REASON = "refund_reason";

    public static final String REFUND_TIME = "refund_time";

    public static final String REFUND_AMOUNT = "refund_amount";

    public static final String REFUND_ORDER_NO = "refund_order_no";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
