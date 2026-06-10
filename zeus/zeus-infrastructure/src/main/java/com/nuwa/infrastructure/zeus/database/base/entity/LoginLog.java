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
 * 登录日志
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LoginLog对象")
public class LoginLog extends Model<LoginLog> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登录IP")
    private String loginIp;

    @ApiModelProperty("登陆浏览器")
    private String browser;

    @ApiModelProperty("登陆时间")
    private Date loginTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String LOGIN_IP = "login_ip";

    public static final String BROWSER = "browser";

    public static final String LOGIN_TIME = "login_time";

}
