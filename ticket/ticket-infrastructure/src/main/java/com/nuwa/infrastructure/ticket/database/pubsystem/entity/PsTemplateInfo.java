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
 * 支付宝小程序模板
 *
 * @author huyonghack@163.com
 * @since 2022-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PsTemplateInfo对象")
public class PsTemplateInfo extends Model<PsTemplateInfo> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("模板名称")
    private String appName;

    @ApiModelProperty("三方应用ID")
    private String appId;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("线上版本")
    private String appVersion;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;


    public static final String ID = "id";

    public static final String APP_NAME = "app_name";

    public static final String APP_ID = "app_id";

    public static final String TEMPLATE_ID = "template_id";

    public static final String APP_VERSION = "app_version";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

}
