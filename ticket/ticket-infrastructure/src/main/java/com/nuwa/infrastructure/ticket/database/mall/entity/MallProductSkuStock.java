package com.nuwa.infrastructure.ticket.database.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import com.nuwa.infrastructure.ticket.util.PriceJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商品规格表
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallProductSkuStock extends Model<MallProductSkuStock> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 规格名称
     */
    private String skuStockName;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 门市价
     */
    @JsonSerialize(using = PriceJson.class)
    private Long marketPrice;

    /**
     * 销售价
     */
    @JsonSerialize(using = PriceJson.class)
    private Long sellPrice;

    /**
     * 规格图
     */
    @JsonSerialize(using = MaterialJson.class)
    private String skuStockImg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志  0正常1删除
     */
    private Integer deleteFlag;

    /**
     * 发布人
     */
    private Long mchId;

    /**
     * 创建用户
     */
    private String createBy;

    /**
     * 更新用户
     */
    private String updateBy;


    public static final String ID = "id";

    public static final String PRODUCT_ID = "product_id";

    public static final String SKU_STOCK_NAME = "sku_stock_name";

    public static final String STOCK = "stock";

    public static final String MARKET_PRICE = "market_price";

    public static final String SELL_PRICE = "sell_price";

    public static final String SKU_STOCK_IMG = "sku_stock_img";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String MCH_ID = "mch_id";

    public static final String CREATE_BY = "create_by";

    public static final String UPDATE_BY = "update_by";

}
