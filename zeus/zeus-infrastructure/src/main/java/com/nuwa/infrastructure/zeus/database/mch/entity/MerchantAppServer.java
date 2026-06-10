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
 * 商户应用服务信息
 *
 * @author huyonghack@163.com
 * @since 2022-06-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantAppServer对象")
public class MerchantAppServer extends Model<MerchantAppServer> {
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

    @ApiModelProperty("状态 0:已过期 1:正常")
    private Integer status;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private String createUserId;


    public static final String ID = "id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String APP_NAME = "app_name";

    public static final String APP_ID = "app_id";

    public static final String PARENT_APP_ID = "parent_app_id";

    public static final String STATUS = "status";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

}
