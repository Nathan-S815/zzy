package com.nuwa.infrastructure.ticket.database.gateway.entity;

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
 * 支付中心配置参数
 *
 * @author huyonghack@163.com
 * @since 2022-04-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantPayGatewayConfig对象")
public class MerchantPayGatewayConfig extends Model<MerchantPayGatewayConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("支付平台商户号")
    private String gatewayMchId;

    @ApiModelProperty("密钥")
    private String gatewaySecretKey;

    @ApiModelProperty("支付方式 小程序：alipay_mini 服务商小程序alipay_mini_service  小程序模板:alipay_mini_template")
    private String gatewayPayType;

    @ApiModelProperty("支付应用id")
    private String channelAppId;

    @ApiModelProperty("支付商户id")
    private String channelMerchantId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("扩展参数")
    private String extend;

    @ApiModelProperty("状态,0-停止使用,1-使用中")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String GATEWAY_MCH_ID = "gateway_mch_id";

    public static final String GATEWAY_SECRET_KEY = "<REDACTED>";

    public static final String GATEWAY_PAY_TYPE = "gateway_pay_type";

    public static final String CHANNEL_APP_ID = "channel_app_id";

    public static final String CHANNEL_MERCHANT_ID = "channel_merchant_id";

    public static final String NAME = "name";

    public static final String EXTEND = "extend";

    public static final String STATUS = "status";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

}
