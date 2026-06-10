package com.nuwa.infrastructure.ticket.database.product.entity;

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
 * 景区产品门票有效期规则设置
 *
 * @author huyonghack@163.com
 * @since 2021-12-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotProductValidPeriodConfig对象")
public class ScenicspotProductValidPeriodConfig extends Model<ScenicspotProductValidPeriodConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Integer deleteFlag;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("规则模式 0:游玩日当天有效,1:游玩日延后n天有效,2:预订日延后n天有效,3:指定日期范围有效")
    private Integer ruleMode;

    @ApiModelProperty("之后有效天数")
    private Integer afterDay;

    @ApiModelProperty("开始日期（rangeDate模式下有效）")
    private Date startDate;

    @ApiModelProperty("结束日期（rangeDate模式下有效）")
    private Date endDate;

    @ApiModelProperty("景区产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("版本号")
    @Version
    private Long version;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String RULE_MODE = "rule_mode";

    public static final String AFTER_DAY = "after_day";

    public static final String START_DATE = "start_date";

    public static final String END_DATE = "end_date";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String VERSION = "version";

}
