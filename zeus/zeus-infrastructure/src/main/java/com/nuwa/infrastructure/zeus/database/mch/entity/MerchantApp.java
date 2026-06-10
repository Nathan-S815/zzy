package com.nuwa.infrastructure.zeus.database.mch.entity;

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
 * 商户应用
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantApp对象")
public class MerchantApp extends Model<MerchantApp> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("应用id")
    private Long appId;

    @ApiModelProperty("父应用")
    private Long parentAppId;

    @ApiModelProperty("登录提交的url")
    private String loginSubmitUrl;

    @ApiModelProperty("景区编码")
    private String scenicCode;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Integer status;

    @ApiModelProperty("排序字段")
    private Integer orderNum;


    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    private Date updateTime;

    private String updateUserId;

    private String updateUserName;


    public static final String ID = "id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String APP_NAME = "app_name";

    public static final String APP_ID = "app_id";

    public static final String PARENT_APP_ID = "parent_app_id";

    public static final String LOGIN_SUBMIT_URL = "login_submit_url";

    public static final String SCENIC_CODE = "scenic_code";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String ORDER_NUM = "order_num";

}
