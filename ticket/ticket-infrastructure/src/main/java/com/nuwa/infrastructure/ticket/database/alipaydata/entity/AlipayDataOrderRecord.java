package com.nuwa.infrastructure.ticket.database.alipaydata.entity;

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
 * 支付宝-景区订单同步记录
 *
 * @author huyonghack@163.com
 * @since 2022-05-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlipayDataOrderRecord对象")
public class AlipayDataOrderRecord extends Model<AlipayDataOrderRecord> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("景区小程序id")
    private String alipayAppId;

    @ApiModelProperty("订单外部id")
    private String outerOrderId;

    @ApiModelProperty("下时间")
    private Date createTime;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("支付宝订单id")
    private String alipayOrderId;

    @ApiModelProperty("备注")
    private String remark;


    public static final String ID = "id";

    public static final String ALIPAY_APP_ID = "alipay_app_id";

    public static final String OUTER_ORDER_ID = "outer_order_id";

    public static final String CREATE_TIME = "create_time";

    public static final String ORDER_STATUS = "order_status";

    public static final String ALIPAY_ORDER_ID = "alipay_order_id";

    public static final String REMARK = "remark";

}
