package com.nuwa.infrastructure.zeus.database.base.entity;

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
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseUser对象")
public class BaseUser extends Model<BaseUser> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("手机号")
    private String mobilePhone;

    @ApiModelProperty("座机")
    private String telPhone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("类型(0:普通用户 1:超级管理员)")
    private String type;

    @ApiModelProperty("租户id(-1:总平台)")
    private Long tenantId;

    @ApiModelProperty("租户appid(-1:所有)")
    private Long tenantAppId;

    @ApiModelProperty("状态[0正常 1禁用]")
    private String status;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("登录次数")
    private Long loginTimes;

    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    private Date updateTime;

    private String updateUserName;

    private String updateUserId;

    private String updateHost;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String NAME = "name";

    public static final String BIRTHDAY = "birthday";

    public static final String ADDRESS = "address";

    public static final String MOBILE_PHONE = "mobile_phone";

    public static final String TEL_PHONE = "tel_phone";

    public static final String EMAIL = "email";

    public static final String SEX = "sex";

    public static final String TYPE = "type";

    public static final String TENANT_ID = "tenant_id";

    public static final String TENANT_APP_ID = "tenant_app_id";

    public static final String STATUS = "status";

    public static final String DESCRIPTION = "description";

    public static final String LOGIN_TIMES = "login_times";

    public static final String LAST_LOGIN_IP = "last_login_ip";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_HOST = "update_host";

}
