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
 * 小程序页面信息
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppPageInfo对象")
public class AppPageInfo extends Model<AppPageInfo> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("所属AppId")
    private Long appId;

    @ApiModelProperty("页面名称")
    private String pageName;

    @ApiModelProperty("页面描述")
    private String pageDescribe;

    @ApiModelProperty("页面地址")
    private String pageUri;

    @ApiModelProperty("创建时间IM")
    private Date createTime;

    @ApiModelProperty("更新时间IM")
    private Date updateTime;

    @ApiModelProperty("删除标志 [0正常 1删除]IM")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String PAGE_NAME = "page_name";

    public static final String PAGE_DESCRIBE = "page_describe";

    public static final String PAGE_URI = "page_uri";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
