package com.nuwa.infrastructure.zeus.database.base.entity;

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
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseMenu对象")
public class BaseMenu extends Model<BaseMenu> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("路径编码")
    private String code;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("父级节点")
    private Integer parentId;

    @ApiModelProperty("资源路径")
    private String href;

    @ApiModelProperty("图标")
    private String icon;

    private String type;

    @ApiModelProperty("排序")
    private Integer orderNum;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("菜单上下级关系")
    private String path;

    @ApiModelProperty("应用id(-1:代表平台菜单 其他:应用菜单)")
    private Long appId;

    @ApiModelProperty("启用禁用")
    private String enabled;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    private Date updateTime;

    private String updateUserName;

    private String updateUserId;

    private String updateHost;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String TITLE = "title";

    public static final String PARENT_ID = "parent_id";

    public static final String HREF = "href";

    public static final String ICON = "icon";

    public static final String TYPE = "type";

    public static final String ORDER_NUM = "order_num";

    public static final String DESCRIPTION = "description";

    public static final String PATH = "path";

    public static final String APP_ID = "app_id";

    public static final String ENABLED = "enabled";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_HOST = "update_host";

}
