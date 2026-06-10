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
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MallProduct extends Model<MallProduct> {
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
     * 商品名称
     */
    private String productName;

    /**
     * 一级分类
     */
    private Long classificationFirstId;

    /**
     * 二级分类
     */
    private Long classificationSecondId;

    /**
     * 三级分类
     */
    private Long classificationThirdId;

    /**
     * 发货地省
     */
    private String departurePlaceProvince;

    /**
     * 发货地省名称
     */
    private String departurePlaceProvinceName;

    /**
     * 发货地市
     */
    private String departurePlaceCity;

    /**
     * 发货地市名称
     */
    private String departurePlaceCityName;

    /**
     * 发货地区
     */
    private String departurePlaceCounty;

    /**
     * 发货地区名称
     */
    private String departurePlaceCountyName;

    /**
     * 封面图
     */
    @JsonSerialize(using = MaterialJson.class)
    private String coverImg;

    /**
     * 轮播图
     */
    private String carouselImgs;

    /**
     * 客服电话
     */
    private String servicePhone;

    /**
     * 门店ID
     */
    private String storeId;

    /**
     * 规格Id(多个id逗号分隔)
     */
    private String specificationsId;

    /**
     * 物流配置  0包邮1到付
     */
    private Integer expressType;

    /**
     * 商品介绍
     */
    private String commodityIntroduce;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 最低价格
     */
    @JsonSerialize(using = PriceJson.class)
    private Long lowPrice;

    /**
     * 发货地址
     */
    private String departurePlace;

    /**
     * 上下架状态  10上架11下架
     */
    private Integer publishStatus;

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


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY_ID = "city_id";

    public static final String COUNTY_ID = "county_id";

    public static final String CREATE_BY = "create_by";

    public static final String UPDATE_BY = "update_by";

    public static final String PRODUCT_NAME = "product_name";

    public static final String CLASSIFICATION_FIRST_ID = "classification_first_id";

    public static final String CLASSIFICATION_SECOND_ID = "classification_second_id";

    public static final String CLASSIFICATION_THIRD_ID = "classification_third_id";

    public static final String DEPARTURE_PLACE_PROVINCE = "departure_place_province";

    public static final String DEPARTURE_PLACE_PROVINCE_NAME = "departure_place_province_name";

    public static final String DEPARTURE_PLACE_CITY = "departure_place_city";

    public static final String DEPARTURE_PLACE_CITY_NAME = "departure_place_city_name";

    public static final String DEPARTURE_PLACE_COUNTY = "departure_place_county";

    public static final String DEPARTURE_PLACE_COUNTY_NAME = "departure_place_county_name";

    public static final String COVER_IMG = "cover_img";

    public static final String CAROUSEL_IMGS = "carousel_imgs";

    public static final String SERVICE_PHONE = "service_phone";

    public static final String STORE_ID = "store_id";

    public static final String SPECIFICATIONS_ID = "specifications_id";

    public static final String EXPRESS_TYPE = "express_type";

    public static final String COMMODITY_INTRODUCE = "commodity_introduce";

    public static final String SALES = "sales";

    public static final String LOW_PRICE = "low_price";

    public static final String DEPARTURE_PLACE = "departure_place";

    public static final String PUBLISH_STATUS = "publish_status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
