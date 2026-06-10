package com.nuwa.infrastructure.ticket.database.scenicspot.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 景区poi
 *
 * @author huyonghack@163.com
 * @since 2021-12-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Scenicspot对象")
public class Scenicspot extends Model<Scenicspot> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("网站")
    private String site;

    @ApiModelProperty("介绍")
    private String memo;

    @ApiModelProperty("联系电话")
    private String tel;

    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("区名称")
    private String area;

    @ApiModelProperty("区id")
    private String areaId;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("优惠政策")
    private String preferential;

    @ApiModelProperty("温馨提示")
    private String tips;

    @ApiModelProperty("状态[-1:草稿 0:待审核;1:审核通过;2审核不通过]")
    private Integer status;

    @ApiModelProperty("综合评分")
    private BigDecimal comprehensiveScore;

    @ApiModelProperty("综合评分")
    private BigDecimal healthScore;

    @ApiModelProperty("卫生评分")
    private BigDecimal serviceScore;

    @ApiModelProperty("服务评分")
    private BigDecimal facilityScore;

    @ApiModelProperty("环境评分")
    private BigDecimal environmentScore;

    @ApiModelProperty("排序权重(越小越靠前)")
    private Integer weight;

    @ApiModelProperty("联系人姓名")
    private String salesman;

    @ApiModelProperty("联系人电话")
    private String salesmanTelephone;

    @ApiModelProperty("起售价格")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal priceMin;

    @ApiModelProperty("景区等级（1,2,3,4,5）")
    private Integer grade;

    @ApiModelProperty("开放时间")
    private String openTime;

    @ApiModelProperty("用时建议")
    private String useTimeProposal;

    @ApiModelProperty("游玩季节")
    private String bestPlaySeason;

    @ApiModelProperty("适合人群；0->所有人 1->成年人 2->儿童 3->学生 4->本地市民 5->教师  6->记者 7->导游 8->医护人员 9->残疾人 10->外籍认识 11->现役军人  12->离休干部 13->其他")
    private String suitedPeople;

    @ApiModelProperty("交通信息")
    private String trafficInfo;

    @ApiModelProperty("拒绝原因")
    private String rejectReason;

    @ApiModelProperty("展示主图")
    private String mainPicture;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("版本标志[1正式 0副本]")
    private Integer versionFlag;

    @ApiModelProperty("原始id")
    private Long srcId;

    @ApiModelProperty("最后更新价格时间")
    private Date lastUpdatePriceTime;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    private String poiType;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LOGO = "logo";

    public static final String SITE = "site";

    public static final String MEMO = "memo";

    public static final String TEL = "tel";

    public static final String PROVINCE = "province";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY = "city";

    public static final String CITY_ID = "city_id";

    public static final String AREA = "area";

    public static final String AREA_ID = "area_id";

    public static final String TYPE_NAME = "type_name";

    public static final String ADDRESS = "address";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String PREFERENTIAL = "preferential";

    public static final String TIPS = "tips";

    public static final String STATUS = "status";

    public static final String COMPREHENSIVE_SCORE = "comprehensive_score";

    public static final String HEALTH_SCORE = "health_score";

    public static final String SERVICE_SCORE = "service_score";

    public static final String FACILITY_SCORE = "facility_score";

    public static final String ENVIRONMENT_SCORE = "environment_score";

    public static final String WEIGHT = "weight";

    public static final String SALESMAN = "salesman";

    public static final String SALESMAN_TELEPHONE = "salesman_telephone";

    public static final String PRICE_MIN = "price_min";

    public static final String GRADE = "grade";

    public static final String OPEN_TIME = "open_time";

    public static final String USE_TIME_PROPOSAL = "use_time_proposal";

    public static final String BEST_PLAY_SEASON = "best_play_season";

    public static final String SUITED_PEOPLE = "suited_people";

    public static final String TRAFFIC_INFO = "traffic_info";

    public static final String REJECT_REASON = "reject_reason";

    public static final String MAIN_PICTURE = "main_picture";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String VERSION_FLAG = "version_flag";

    public static final String SRC_ID = "src_id";

    public static final String LAST_UPDATE_PRICE_TIME = "last_update_price_time";

    public static final String POI_TYPE = "poi_type";

}
