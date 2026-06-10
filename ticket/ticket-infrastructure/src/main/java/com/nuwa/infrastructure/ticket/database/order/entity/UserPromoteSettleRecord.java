package com.nuwa.infrastructure.ticket.database.order.entity;

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
 * 用户推广结算记录
 *
 * @author huyonghack@163.com
 * @since 2022-01-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserPromoteSettleRecord对象")
public class UserPromoteSettleRecord extends Model<UserPromoteSettleRecord> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("结算状态 1:已核销，待结算  2:已结算")
    private Integer status;

    @ApiModelProperty("游玩日期")
    private Date visitDate;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("推广人标识")
    private String promoterCode;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("结算时间")
    private Date settleTime;


    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String AMOUNT = "amount";

    public static final String QUANTITY = "quantity";

    public static final String STATUS = "status";

    public static final String VISIT_DATE = "visit_date";

    public static final String PRODUCT_NAME = "product_name";

    public static final String PRODUCT_ID = "product_id";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String PROMOTER_CODE = "promoter_code";

    public static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String SETTLE_TIME = "settle_time";

}
