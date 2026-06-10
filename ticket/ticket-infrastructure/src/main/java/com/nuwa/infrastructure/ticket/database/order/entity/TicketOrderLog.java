package com.nuwa.infrastructure.ticket.database.order.entity;

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
 * 订单日志
 *
 * @author huyonghack@163.com
 * @since 2021-12-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TicketOrderLog对象")
public class TicketOrderLog extends Model<TicketOrderLog> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("时间")
    private Date createDate;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单日志类型 1:创建订单 2:支付通道下单  3:供应商下单成功 4:供应商下单失败 5:退款成功 6:退款失败 7:订单已完成 8:订单已关闭")
    private Integer type;

    @ApiModelProperty("业务订单id")
    private Long bizOrderId;

    @ApiModelProperty("结果描述")
    private String result;


    public static final String ID = "id";

    public static final String CREATE_DATE = "create_date";

    public static final String ORDER_ID = "order_id";

    public static final String TYPE = "type";

    public static final String BIZ_ORDER_ID = "biz_order_id";

    public static final String RESULT = "result";

}
