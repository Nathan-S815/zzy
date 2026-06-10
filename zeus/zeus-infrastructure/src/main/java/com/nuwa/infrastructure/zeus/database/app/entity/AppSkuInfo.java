package com.nuwa.infrastructure.zeus.database.app.entity;

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
 * app规格
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppSkuInfo对象")
public class AppSkuInfo extends Model<AppSkuInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("AppID")
    private Long appId;

    @ApiModelProperty("规格名称")
    private String appSkuName;

    @ApiModelProperty("是否面议[0否 1是]")
    private String negotiable;

    @ApiModelProperty("服务周期")
    private String validity;

    @ApiModelProperty("服务周期单位[DAY,WEEK,MONTH,YEAR]")
    private String validityUnit;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String APP_SKU_NAME = "app_sku_name";

    public static final String NEGOTIABLE = "negotiable";

    public static final String VALIDITY = "validity";

    public static final String VALIDITY_UNIT = "validity_unit";

    public static final String PRICE = "price";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
