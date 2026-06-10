package com.nuwa.infrastructure.ticket.database.pubsystem.entity;

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
 * 支付宝模板参数配置
 *
 * @author huyonghack@163.com
 * @since 2022-05-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PsAlipayConfig对象")
public class PsAlipayConfig extends Model<PsAlipayConfig> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("应用名")
    private String appName;

    @ApiModelProperty("PID")
    private String pid;

    @ApiModelProperty("开放平台网关")
    private String serverUrl;

    @ApiModelProperty("三方应用ID")
    private String appId;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("应用公钥")
    private String publicKey;

    @ApiModelProperty("应用私钥")
    private String privateKey;

    @ApiModelProperty("服务商id")
    private String isvId;

    @ApiModelProperty("服务商名称")
    private String isvName;

    @ApiModelProperty("支付宝公钥")
    private String alipayPublicKey;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String APP_NAME = "app_name";

    public static final String PID = "pid";

    public static final String SERVER_URL = "server_url";

    public static final String APP_ID = "app_id";

    public static final String TEMPLATE_ID = "template_id";

    public static final String PUBLIC_KEY = "public_key";

    public static final String PRIVATE_KEY = "private_key";

    public static final String ALIPAY_PUBLIC_KEY = "alipay_public_key";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    public static final String ISV_ID = "isv_id";

    public static final String ISV_NAME = "isv_name";

}
