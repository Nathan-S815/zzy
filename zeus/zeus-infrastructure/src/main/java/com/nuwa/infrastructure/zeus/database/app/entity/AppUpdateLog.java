package com.nuwa.infrastructure.zeus.database.app.entity;

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
 * app升级日志
 *
 * @author huyonghack@163.com
 * @since 2022-06-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppUpdateLog对象")
public class AppUpdateLog extends Model<AppUpdateLog> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("appId")
    private Long appId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("版本号")
    @Version
    private String version;

    @ApiModelProperty("版本内容")
    private String detail;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("发布时间")
    private Date upgradeDate;

    @ApiModelProperty("删除标志[0正常 1删除]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String TITLE = "title";

    public static final String VERSION = "version";

    public static final String DETAIL = "detail";

    public static final String CREATE_TIME = "create_time";

    public static final String UPGRADE_DATE = "upgrade_date";

    public static final String DELETE_FLAG = "delete_flag";

}
