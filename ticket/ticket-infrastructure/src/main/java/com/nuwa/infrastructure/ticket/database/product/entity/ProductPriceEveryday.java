package com.nuwa.infrastructure.ticket.database.product.entity;

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
 * 产品价格日历
 *
 * @author huyonghack@163.com
 * @since 2022-01-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductPriceEveryday对象")
public class ProductPriceEveryday extends Model<ProductPriceEveryday> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("日期")
    private Date date;

    @ApiModelProperty("库存模式 0:非场次 1：场次")
    private Integer stockModel;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("销售数量")
    private Long setterNumber;

    @ApiModelProperty("销售价")
    private BigDecimal salePrice;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    @ApiModelProperty("状态 0:正常 1:删除")
    private Integer status;

    @ApiModelProperty("提前预定天数")
    private Integer beforeDay;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    private String createByName;

    private String createById;

    private Date createTime;


    public static final String ID = "id";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String MONTH = "month";

    public static final String DATE = "date";

    public static final String STOCK_MODEL = "stock_model";

    public static final String STOCK_NUMBER = "stock_number";

    public static final String SETTER_NUMBER = "setter_number";

    public static final String SALE_PRICE = "sale_price";

    public static final String MARKET_PRICE = "market_price";

    public static final String PURCHASE_PRICE = "purchase_price";

    public static final String STATUS = "status";

    public static final String BEFORE_DAY = "before_day";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_TIME = "create_time";

}
