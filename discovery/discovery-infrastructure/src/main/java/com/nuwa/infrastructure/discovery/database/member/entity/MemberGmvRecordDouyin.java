package com.nuwa.infrastructure.discovery.database.member.entity;

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
 * 抖音订单表
 *
 * @author huyonghack@163.com
 * @since 2022-08-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberGmvRecordDouyin对象")
public class MemberGmvRecordDouyin extends Model<MemberGmvRecordDouyin> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("base记录id")
    private Long baseId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("第三方平台账号")
    private String thirdPartyAccount;

    @ApiModelProperty("第三方平台昵称")
    private String thirdPartyNike;

    @ApiModelProperty("第三方平台订单id")
    private String thirdPartyOrderId;

    @ApiModelProperty("第三方平台商品id")
    private String thirdPartyCommodityId;

    @ApiModelProperty("价格 单位：分")
    private Integer price;

    @ApiModelProperty("支付时间")
    private Date paymentTime;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String BASE_ID = "base_id";

    public static final String USER_ID = "user_id";

    public static final String THIRD_PARTY_ACCOUNT = "third_party_account";

    public static final String THIRD_PARTY_NIKE = "third_party_nike";

    public static final String THIRD_PARTY_ORDER_ID = "third_party_order_id";

    public static final String THIRD_PARTY_COMMODITY_ID = "third_party_commodity_id";

    public static final String PRICE = "price";

    public static final String PAYMENT_TIME = "payment_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
