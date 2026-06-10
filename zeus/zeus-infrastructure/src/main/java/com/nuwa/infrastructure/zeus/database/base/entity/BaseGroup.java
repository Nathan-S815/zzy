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
@ApiModel(value = "BaseGroup对象")
public class BaseGroup extends Model<BaseGroup> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色编码")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("上级节点")
    private Integer parentId;

    @ApiModelProperty("树状关系")
    private String path;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("角色组类型")
    private Integer groupType;

    @ApiModelProperty("状态 on | off")
    private String status;

    @ApiModelProperty("租户id(-1:总平台)")
    private Long tenantId;

    @ApiModelProperty("扩展字段")
    private String extend;

    private String description;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    private Date updateTime;

    private String updateUserId;

    private String updateUserName;

    private String updateHost;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parent_id";

    public static final String PATH = "path";

    public static final String TYPE = "type";

    public static final String GROUP_TYPE = "group_type";

    public static final String TENANT_ID = "tenant_id";

    public static final String DESCRIPTION = "description";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String UPDATE_HOST = "update_host";

    public static final String EXTEND = "extend";

}
