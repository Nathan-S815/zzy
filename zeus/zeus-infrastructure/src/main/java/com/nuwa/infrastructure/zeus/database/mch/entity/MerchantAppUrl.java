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
 * app跳转链接
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantAppUrl对象")
public class MerchantAppUrl extends Model<MerchantAppUrl> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    private Long mchId;

    @ApiModelProperty("AppID")
    private Long appId;

    @ApiModelProperty("登录提交的url")
    private String loginSubmitUrl;

    @ApiModelProperty("景区编码")
    private String scenicCode;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String LOGIN_SUBMIT_URL = "login_submit_url";

    public static final String SCENIC_CODE = "scenic_code";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
