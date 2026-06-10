package com.nuwa.infrastructure.zeus.database.base.entity;

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
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BaseElement对象")
public class BaseElement extends Model<BaseElement> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资源编码")
    private String code;

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("资源路径")
    private String uri;

    @ApiModelProperty("资源关联菜单")
    private String menuId;

    private String parentId;

    @ApiModelProperty("资源树状检索路径")
    private String path;

    @ApiModelProperty("资源请求类型")
    private String method;

    @ApiModelProperty("描述")
    private String description;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String TYPE = "type";

    public static final String NAME = "name";

    public static final String URI = "uri";

    public static final String MENU_ID = "menu_id";

    public static final String PARENT_ID = "parent_id";

    public static final String PATH = "path";

    public static final String METHOD = "method";

    public static final String DESCRIPTION = "description";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

}
