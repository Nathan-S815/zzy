package com.nuwa.infrastructure.discovery.database.user.entity;

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
 * 达人三方账户表
 *
 * @author huyonghack@163.com
 * @since 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ThirdUser对象")
public class ThirdUser extends Model<ThirdUser> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("渠道 weixn,douyin")
    private String channelCode;

    @ApiModelProperty("用户是否被禁用，默认0表示正常用户，-1表示黑名单被禁用的用户")
    private Integer isDisabled;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String USER_ID = "user_id";

    public static final String ID = "id";

    public static final String OPEN_ID = "open_id";

    public static final String CHANNEL_CODE = "channel_code";

    public static final String IS_DISABLED = "is_disabled";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
