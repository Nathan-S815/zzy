package com.nuwa.infrastructure.ticket.database.mchconfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户App基本信息
 *
 * @author huyonghack@163.com
 * @since 2022-04-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantAppBaseConf对象")
public class MerchantAppBaseConf extends Model<MerchantAppBaseConf> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("小程序模板id")
    private String appTemplateId;

    @ApiModelProperty("小程序三方应用id")
    private String thirdAppId;

    @ApiModelProperty("应用类型")
    private String appType;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("所属省份id")
    private Long provinceId;

    @ApiModelProperty("所属地市id")
    private Long cityId;

    @ApiModelProperty("抖音小程序的 APP Secret")
    private String secret;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("地市名称")
    private String cityName;

    @ApiModelProperty("类型 SINGLE_SCENIC:单景点 PLATE:全域")
    private String type;

    @ApiModelProperty("poi id")
    private Long poiId;

    @ApiModelProperty("poi id list")
    private String poiList;

    @ApiModelProperty("默认小程序 1:默认 0:普通")
    private Integer defaultFlag;

    @ApiModelProperty("渠道公钥")
    private String channelPublicKey;

    @ApiModelProperty("商户私钥")
    private String mchPrivateKey;

    @ApiModelProperty("商户授权码")
    private String appAuthCode;


    public static final String ID = "id";

    public static final String OUT_APP_ID = "out_app_id";

    public static final String APP_TYPE = "app_type";

    public static final String MCH_ID = "mch_id";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY_ID = "city_id";

    public static final String SECRET = "<REDACTED>";

    public static final String APP_NAME = "app_name";

    public static final String PROVINCE_NAME = "province_name";

    public static final String CITY_NAME = "city_name";

    public static final String TYPE = "type";

    public static final String POI_ID = "poi_id";

    public static final String POI_LIST = "poi_list";

    public static final String DEFAULT_FLAG = "default_flag";

    public static final String CHANNEL_PUBLIC_KEY = "channel_public_key";

    public static final String MCH_PRIVATE_KEY = "mch_private_key";

    public static final String APP_AUTH_CODE = "app_auth_code";

    public static final String APP_TEMPLATE_ID = "app_template_id";

    public static final String THIRD_APP_ID = "third_app_id";

}
