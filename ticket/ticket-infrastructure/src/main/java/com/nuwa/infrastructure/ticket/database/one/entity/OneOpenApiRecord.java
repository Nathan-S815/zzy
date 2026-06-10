package com.nuwa.infrastructure.ticket.database.one.entity;

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
 * 一码通调用记录
 *
 * @author huyonghack@163.com
 * @since 2022-10-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneOpenApiRecord对象")
public class OneOpenApiRecord extends Model<OneOpenApiRecord> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("一码通平台商户id")
    private Long oneMchId;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("一码通平台客户端id")
    private Long oneCleintId;

    @ApiModelProperty("一码通平台客户端appid")
    private String oneCleintAppId;

    @ApiModelProperty("一码通平台客户端名称")
    private String oneCleintName;

    @ApiModelProperty("用户身份")
    private String identityCode;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("身份证姓名")
    private String idName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("景博名称")
    private String scenicspotName;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("扫码设备客户端appId")
    private Long scanClientAppId;

    @ApiModelProperty("扫码设备客户端名称")
    private String scanClientAppName;

    @ApiModelProperty("扫码商户id")
    private Long scanMchId;

    @ApiModelProperty("权益信息")
    private String rightsInfo;

    @ApiModelProperty("选择的权益id")
    private Long rightsId;

    @ApiModelProperty("扫码客户端类型 service_open_client|service_one_merchant_client")
    private String scanClientType;

    @ApiModelProperty("记录时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String ONE_MCH_ID = "one_mch_id";

    public static final String MCH_NAME = "mch_name";

    public static final String ONE_CLEINT_ID = "one_cleint_id";

    public static final String ONE_CLEINT_APP_ID = "one_cleint_app_id";

    public static final String ONE_CLEINT_NAME = "one_cleint_name";

    public static final String IDENTITY_CODE = "identity_code";

    public static final String ID_NO = "id_no";

    public static final String ID_NAME = "id_name";

    public static final String MOBILE = "mobile";

    public static final String SCENICSPOT_NAME = "scenicspot_name";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String SCAN_CLIENT_APP_ID = "scan_client_app_id";

    public static final String SCAN_CLIENT_APP_NAME = "scan_client_app_name";

    public static final String SCAN_MCH_ID = "scan_mch_id";

    public static final String RIGHTS_INFO = "rights_info";

    public static final String RIGHTS_ID = "rights_id";

    public static final String SCAN_CLIENT_TYPE = "scan_client_type";

    public static final String CREATE_TIME = "create_time";

}
