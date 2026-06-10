package com.nuwa.infrastructure.ticket.database.one.entity;

import java.math.BigDecimal;
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
 * 一码通会员
 *
 * @author huyonghack@163.com
 * @since 2022-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneMember对象")
public class OneMember extends Model<OneMember> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID，主键，自动增长")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户登录账户")
    private String userAccount;

    @ApiModelProperty("用户登录密码")
    private String userPassword;

    @ApiModelProperty("用户昵称")
    private String userNike;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("用户虚拟头像")
    private String userImg;

    @ApiModelProperty("用户真实姓名")
    private String userRealName;

    @ApiModelProperty("用户性别")
    private Integer userSex;

    @ApiModelProperty("用户邮箱")
    private String userEmail;

    @ApiModelProperty("用户生日")
    private String userBirthday;

    @ApiModelProperty("用户所在地区，城市-区县")
    private String region;

    @ApiModelProperty("用户余额")
    private BigDecimal balance;

    @ApiModelProperty("会员等级id，预留字段")
    private Integer userLevelId;

    @ApiModelProperty("用户注册ip")
    private String ip;

    @ApiModelProperty("来源appId")
    private Long srcAppId;

    @ApiModelProperty("来源appName")
    private String srcAppName;

    @ApiModelProperty("来源app类型(weixin_mp)")
    private String srcAppType;

    @ApiModelProperty("设备mac地址。任何终端都有mac地址。")
    private String mac;

    @ApiModelProperty("用户是否被禁用，默认0表示正常用户，-1表示黑名单被禁用的用户")
    private Integer isDisabled;

    @ApiModelProperty("所属商户id")
    private Long mchId;

    @ApiModelProperty("用户身份")
    private String identityCode;

    @ApiModelProperty("分享code")
    private String shareCode;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String USER_ID = "user_id";

    public static final String USER_ACCOUNT = "user_account";

    public static final String USER_PASSWORD = "user_password";

    public static final String USER_NIKE = "user_nike";

    public static final String USER_PHONE = "user_phone";

    public static final String USER_IMG = "user_img";

    public static final String USER_REAL_NAME = "user_real_name";

    public static final String USER_SEX = "user_sex";

    public static final String USER_EMAIL = "user_email";

    public static final String USER_BIRTHDAY = "user_birthday";

    public static final String REGION = "region";

    public static final String BALANCE = "balance";

    public static final String USER_LEVEL_ID = "user_level_id";

    public static final String IP = "ip";

    public static final String SRC_APP_ID = "src_app_id";

    public static final String SRC_APP_NAME = "src_app_name";

    public static final String SRC_APP_TYPE = "src_app_type";

    public static final String MAC = "mac";

    public static final String IS_DISABLED = "is_disabled";

    public static final String MCH_ID = "mch_id";

    public static final String IDENTITY_CODE = "identity_code";

    public static final String SHARE_CODE = "share_code";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
