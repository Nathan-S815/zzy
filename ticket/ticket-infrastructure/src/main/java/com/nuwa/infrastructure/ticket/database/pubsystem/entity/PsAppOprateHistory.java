package com.nuwa.infrastructure.ticket.database.pubsystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 小程序操作历史
 *
 * @author huyonghack@163.com
 * @since 2022-05-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PsAppOprateHistory对象")
public class PsAppOprateHistory extends Model<PsAppOprateHistory> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("UUID")
    private String uuid;

    @ApiModelProperty("应用APPID")
    private String appId;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("模板版本号")
    private String templateVersion;

    @ApiModelProperty("版本号")
    private String appVersion;

    @ApiModelProperty("构建状态")
    private String buildStatus;

    @ApiModelProperty("审核状态")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String auditStatus;

    @ApiModelProperty("上架状态")
    private String publishStatus;

    @ApiModelProperty("开发状态")
    private String developStatus;

    @ApiModelProperty("受否需要轮询获取结果 0:需要 1:不需要")
    private Integer needRotation;

    @ApiModelProperty("构建时间")
    private Date gmtBuild;

    @ApiModelProperty("审核时间")
    private Date gmtAudit;

    @ApiModelProperty("上架时间")
    private Date gmtPublish;

    @ApiModelProperty("审核失败原因")
    private String auditRejectReason;

    @ApiModelProperty("JSON")
    private String json;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    @ApiModelProperty("体验版状态")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String experienceStatus;

    @ApiModelProperty("体验版本URL")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String expQrCodeUrl;


    public static final String ID = "id";

    public static final String UUID = "uuid";

    public static final String APP_ID = "app_id";

    public static final String APP_NAME = "app_name";

    public static final String TEMPLATE_ID = "template_id";

    public static final String TEMPLATE_VERSION = "template_version";

    public static final String APP_VERSION = "app_version";

    public static final String BUILD_STATUS = "build_status";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String PUBLISH_STATUS = "publish_status";

    public static final String DEVELOP_STATUS = "develop_status";

    public static final String NEED_ROTATION = "need_rotation";

    public static final String GMT_BUILD = "gmt_build";

    public static final String GMT_AUDIT = "gmt_audit";

    public static final String GMT_PUBLISH = "gmt_publish";

    public static final String AUDIT_REJECT_REASON = "audit_reject_reason";

    public static final String JSON = "json";

    public static final String GMT_CREATE = "gmt_create";

    public static final String GMT_MODIFIED = "gmt_modified";

    public static final String EXPERIENCE_STATUS = "experience_status";

    public static final String EXP_QR_CODE_URL = "exp_qr_code_url";

}
