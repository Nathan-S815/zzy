package com.nuwa.infrastructure.ticket.database.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单核销凭证表
 *
 * @author huyonghack@163.com
 * @since 2021-12-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderVoucher对象")
public class OrderVoucher extends Model<OrderVoucher> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("联系人姓名")
    private String name;

    @ApiModelProperty("联系人手机号")
    private String mobile;

    @ApiModelProperty("身份信息")
    private String idCard;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderNo;

    @ApiModelProperty("凭证编码")
    private String voucherCode;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String NAME = "name";

    public static final String MOBILE = "mobile";

    public static final String ID_CARD = "id_card";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_NO = "order_no";

    public static final String VOUCHER_CODE = "voucher_code";

}
