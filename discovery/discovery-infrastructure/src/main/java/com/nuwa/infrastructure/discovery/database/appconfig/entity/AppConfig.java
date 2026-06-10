package com.nuwa.infrastructure.discovery.database.appconfig.entity;

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
 * 三方app对应配置
 *
 * @author huyonghack@163.com
 * @since 2022-09-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppConfig对象")
public class AppConfig extends Model<AppConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID，主键，自动增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("appId")
    private String appId;

    @ApiModelProperty("appSecret")
    private String appSecret;

    @ApiModelProperty("商户id")
    private String mchId;

    @ApiModelProperty("类型 1：微信 ")
    private Integer type;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("创建时间IM")
    private Date createTime;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String APP_SECRET = "<REDACTED>";

    public static final String MCH_ID = "mch_id";

    public static final String TYPE = "type";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_TIME = "create_time";

}
