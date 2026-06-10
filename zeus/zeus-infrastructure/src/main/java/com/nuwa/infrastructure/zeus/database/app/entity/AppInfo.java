package com.nuwa.infrastructure.zeus.database.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 应用信息
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppInfo对象")
public class AppInfo extends Model<AppInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("版本号")
    @Version
    private String version;

    @ApiModelProperty("版本名称")
    private String versionName;

    @ApiModelProperty("logo")
    @JsonSerialize(using = MaterialJson.class)
    private Long logo;

    @ApiModelProperty("应用链接")
    private String manageUrl;

    @ApiModelProperty("应用类型 1:独立应用 2:功能应用")
    private Integer appType;

    @ApiModelProperty("系统提供方 outer |  inner")
    private String provider;

    @ApiModelProperty("免密登录(0:不支持 1:支持)")
    private Integer ssh;

    @ApiModelProperty("私有化部署(0:不支持 1:支持)")
    private Integer privatization;

    @ApiModelProperty("删除标志 [0正常 1删除]IM")
    private Integer deleteFlag;

    @ApiModelProperty("上架状态 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("是否推荐[0否 1是]")
    private Integer recommend;

    @ApiModelProperty("应用简介")
    private String appIntroEditor;

    @ApiModelProperty("应用详情")
    private String appDetailEditor;

    @ApiModelProperty("应用教程")
    private String appCourseEditor;

    @ApiModelProperty("服务商名称")
    private String serviceName;

    @ApiModelProperty("咨询电话")
    private String hotline;

    @ApiModelProperty("技术咨询电话")
    private String technologyHotline;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("销量")
    private Long salesVolume;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    private Date updateTime;

    private String updateUserId;

    private String updateUserName;

    private String updateHost;


    public static final String ID = "id";

    public static final String APP_NAME = "app_name";

    public static final String VERSION = "version";

    public static final String VERSION_NAME = "version_name";

    public static final String LOGO = "logo";

    public static final String MANAGE_URL = "manage_url";

    public static final String APP_TYPE = "app_type";

    public static final String SSH = "ssh";

    public static final String PRIVATIZATION = "privatization";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String STATUS = "status";

    public static final String RECOMMEND = "recommend";

    public static final String APP_INTRO_EDITOR = "app_intro_editor";

    public static final String APP_DETAIL_EDITOR = "app_detail_editor";

    public static final String APP_COURSE_EDITOR = "app_course_editor";

    public static final String SERVICE_NAME = "service_name";

    public static final String HOTLINE = "hotline";

    public static final String TECHNOLOGY_HOTLINE = "technology_hotline";

    public static final String EMAIL = "email";

    public static final String SALES_VOLUME = "sales_volume";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String UPDATE_HOST = "update_host";

    public static final String PROVIDER = "provider";

}
