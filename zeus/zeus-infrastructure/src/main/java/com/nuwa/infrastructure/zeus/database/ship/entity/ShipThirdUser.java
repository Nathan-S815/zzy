package com.nuwa.infrastructure.zeus.database.ship.entity;

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
 * 跳转第三方商户用户信息表
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ShipThirdUser对象")
public class ShipThirdUser extends Model<ShipThirdUser> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    private Long merchantId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("商户应用id")
    private Long merchantAppId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("登录次数")
    private Long loginTimes;

    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("平台类型(Erp,B2B)")
    private String platType;

    @ApiModelProperty("账号状态（0-禁用，1-启用）")
    private Integer status;

    @ApiModelProperty("景区编号")
    private String scenicCode;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("地址")
    private String adminUrl;


    public static final String ID = "id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String MERCHANT_APP_ID = "merchant_app_id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String LOGIN_TIMES = "login_times";

    public static final String LAST_LOGIN_IP = "last_login_ip";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String PLAT_TYPE = "plat_type";

    public static final String STATUS = "status";

    public static final String SCENIC_CODE = "scenic_code";

    public static final String ROLE_NAME = "role_name";

    public static final String ADMIN_URL = "admin_url";

}
