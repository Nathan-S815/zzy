package com.nuwa.infrastructure.ticket.database.product.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 产品管理
 *
 * @author huyonghack@163.com
 * @since 2022-02-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotProduct对象")
public class ScenicspotProduct extends Model<ScenicspotProduct> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("所属景点id")
    private Long scenicspotId;

    @ApiModelProperty("所属商户id")
    private Long merchantId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("产品简称")
    private String shortName;

    @ApiModelProperty("省")
    private String provinceId;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String cityId;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String areaId;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("票种")
    private Integer ticketKind;

    @ApiModelProperty("营业时间说明")
    private String officeHours;

    @ApiModelProperty("成人数量")
    private Integer adultNumber;

    @ApiModelProperty("儿童数量")
    private Integer childNumber;

    @ApiModelProperty("市场价格")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal marketPrice;

    @ApiModelProperty("采购价")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal purchasePrice;

    @ApiModelProperty("价格")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal price;

    @ApiModelProperty("温馨提示")
    private String bookingContent;

    @ApiModelProperty("详细描述")
    private String content;

    @ApiModelProperty("费用包含")
    private String feeInfo;

    @ApiModelProperty("费用不包含")
    private String noFeeInfo;

    @ApiModelProperty("是否含保险 0:不包含 1:包含")
    private Integer includeInsurance;

    @ApiModelProperty("保险信息")
    private String insuranceInfo;

    @ApiModelProperty("产品描述")
    private String productDescription;

    @ApiModelProperty("产品图片列表")
    private String pictureItems;

    @ApiModelProperty("产品主图")
    private String mainPicture;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("价格模式 （0:普通,1:日历）")
    private Integer priceMode;

    @ApiModelProperty("销售模式 直销:1 分销:2")
    private Integer salesMode;

    @ApiModelProperty("有效期开始")
    private Date beginTime;

    @ApiModelProperty("有效期结束")
    private Date endTime;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    private Integer status;

    @ApiModelProperty("审核状态 0:待审核 1:审核通过 2:审核失败")
    private Integer auditStatus;

    @ApiModelProperty("审核失败原因")
    private String auditFailureReason;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("销售总金额")
    private BigDecimal sellMoney;

    @ApiModelProperty("销售数量")
    private Integer sellNumber;

    @ApiModelProperty("服务电话")
    private String servicePhone;

    @ApiModelProperty("上架时间")
    private Date publishTime;

    private Date lastUpdateTime;

    private String lastUpdateById;

    private String lastUpdateByName;

    private String createById;

    private String createByName;

    private Date createTime;

    private Integer deleteFlag;

    @ApiModelProperty("版本号")
    @Version
    private Long version;

    @ApiModelProperty("最后更新价格时间")
    private Date lastUpdatePriceTime;


    public static final String ID = "id";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String NAME = "name";

    public static final String SHORT_NAME = "short_name";

    public static final String PROVINCE_ID = "province_id";

    public static final String PROVINCE = "province";

    public static final String CITY_ID = "city_id";

    public static final String CITY = "city";

    public static final String AREA_ID = "area_id";

    public static final String AREA = "area";

    public static final String TICKET_KIND = "ticket_kind";

    public static final String OFFICE_HOURS = "office_hours";

    public static final String ADULT_NUMBER = "adult_number";

    public static final String CHILD_NUMBER = "child_number";

    public static final String MARKET_PRICE = "market_price";

    public static final String PURCHASE_PRICE = "purchase_price";

    public static final String PRICE = "price";

    public static final String BOOKING_CONTENT = "booking_content";

    public static final String CONTENT = "content";

    public static final String FEE_INFO = "fee_info";

    public static final String NO_FEE_INFO = "no_fee_info";

    public static final String INCLUDE_INSURANCE = "include_insurance";

    public static final String INSURANCE_INFO = "insurance_info";

    public static final String PRODUCT_DESCRIPTION = "product_description";

    public static final String PICTURE_ITEMS = "picture_items";

    public static final String MAIN_PICTURE = "main_picture";

    public static final String WEIGHT = "weight";

    public static final String PRICE_MODE = "price_mode";

    public static final String SALES_MODE = "sales_mode";

    public static final String BEGIN_TIME = "begin_time";

    public static final String END_TIME = "end_time";

    public static final String STATUS = "status";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String AUDIT_FAILURE_REASON = "audit_failure_reason";

    public static final String SUPPLIER_PRODUCT_CODE = "supplier_product_code";

    public static final String SUPPLIER_ID = "supplier_id";

    public static final String SUPPLIER_NAME = "supplier_name";

    public static final String SELL_ORDER = "sell_order";

    public static final String SELL_MONEY = "sell_money";

    public static final String SELL_NUMBER = "sell_number";

    public static final String SERVICE_PHONE = "service_phone";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String VERSION = "version";

    public static final String LAST_UPDATE_PRICE_TIME = "last_update_price_time";

}
