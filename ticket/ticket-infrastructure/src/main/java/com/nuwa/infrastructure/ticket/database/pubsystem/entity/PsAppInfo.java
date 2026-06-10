package com.nuwa.infrastructure.ticket.database.pubsystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jboss.logging.Field;

/**
 * 支付宝-小程序应用
 *
 * @author huyonghack@163.com
 * @since 2022-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PsAppInfo对象")
public class PsAppInfo extends Model<PsAppInfo> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("模板配置id")
    private String alipayConfigId;

    @ApiModelProperty("支付宝appId")
    private String alipayAppId;

    @ApiModelProperty("模板Id")
    private String templateId;

    @ApiModelProperty("模板版本")
    private String templateVersion;

    @ApiModelProperty("商家小程序版本")
    private String appVersion;

    @ApiModelProperty("商家小程序状态")
    private String appStatus;

    @ApiModelProperty("商家授权码")
    private String appAuthToken;

    @ApiModelProperty("商家最新授权码")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String appAuthTokenNew;

    @ApiModelProperty("应用描述")
    private String appDesc;

    @ApiModelProperty("错误描述")
    private String errorDesc;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    @ApiModelProperty("授权时间")
    private Date gmtAuth;

    @ApiModelProperty("最近授权时间")
    private Date gmtNewAuth;

    @ApiModelProperty("上架时间")
    private Date gmtPublish;

    public static final String ID = "id";

    public static final String APP_NAME = "app_name";

    public static final String ALIPAY_CONFIG_ID = "alipay_config_id";

    public static final String ALIPAY_APP_ID = "alipay_app_id";

    public static final String TEMPLATE_ID = "template_id";

    public static final String TEMPLATE_VERSION = "template_version";

    public static final String APP_VERSION = "app_version";

    public static final String APP_STATUS = "app_status";

    public static final String APP_AUTH_TOKEN = "app_auth_token";

    public static final String APP_AUTH_TOKEN_NEW = "app_auth_token_new";

    public static final String APP_DESC = "app_desc";

    public static final String ERROR_DESC = "error_desc";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    public static final String GMT_NEW_AUTH = "gmt_new_auth";

    public static final String GMT_AUTH = "gmt_auth";

    public static final String GMT_PUBLISH = "gmt_publish";

}
