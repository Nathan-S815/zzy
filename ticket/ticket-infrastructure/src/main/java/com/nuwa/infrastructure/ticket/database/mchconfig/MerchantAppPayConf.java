package com.nuwa.infrastructure.ticket.database.mchconfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户App支付参数信息
 *
 * @author huyonghack@163.com
 * @since 2022-01-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantAppPayConf对象")
public class MerchantAppPayConf extends Model<MerchantAppPayConf> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("渠道类型 douyin_mini zzz_gateway")
    private String channelType;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("支付密钥")
    private String salt;

    @ApiModelProperty("渠道商户号")
    private String channelMchId;

    @ApiModelProperty("商户clientId")
    private Long mchClientId;

    public static final String ID = "id";

    public static final String OUT_APP_ID = "out_app_id";

    public static final String CHANNEL_TYPE = "channel_type";

    public static final String MCH_ID = "mch_id";

    public static final String SALT = "salt";

    public static final String CHANNEL_MCH_ID = "channel_mch_id";

    public static final String MCH_CLIENT_ID = "mch_client_id";

}
