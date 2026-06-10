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
 * 产品场次表
 *
 * @author huyonghack@163.com
 * @since 2022-04-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProductDayTime对象")
public class ProductDayTime extends Model<ProductDayTime> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("场次名称")
    private String title;

    @ApiModelProperty("日期")
    private Date date;

    @ApiModelProperty("库存数量")
    private Long stockNumber;

    @ApiModelProperty("销售数量")
    private Long setterNumber;

    @ApiModelProperty("开始时间")
    private Date start;

    @ApiModelProperty("结束时间")
    private Date end;

    @ApiModelProperty("状态 0:正常 1:删除")
    private Integer status;

    @ApiModelProperty("产品id")
    private Long scenicspotProductId;

    @ApiModelProperty("价格日历id")
    private Long productDayId;

    private String lastUpdateByName;

    private String createByName;

    private String createById;

    private Date lastUpdateTime;

    private String lastUpdateById;

    private Date createTime;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String DATE = "date";

    public static final String STOCK_NUMBER = "stock_number";

    public static final String SETTER_NUMBER = "setter_number";

    public static final String START = "start";

    public static final String END = "end";

    public static final String STATUS = "status";

    public static final String SCENICSPOT_PRODUCT_ID = "scenicspot_product_id";

    public static final String PRODUCT_DAY_ID = "product_day_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String CREATE_TIME = "create_time";

}
