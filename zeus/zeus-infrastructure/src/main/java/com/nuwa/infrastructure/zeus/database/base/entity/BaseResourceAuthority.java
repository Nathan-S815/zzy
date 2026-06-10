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
@ApiModel(value = "BaseResourceAuthority对象")
public class BaseResourceAuthority extends Model<BaseResourceAuthority> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色ID")
    private String authorityId;

    @ApiModelProperty("角色类型(group:系统角色，app:应用角色)")
    private String authorityType;

    @ApiModelProperty("资源ID")
    private String resourceId;

    @ApiModelProperty("资源类型")
    private String resourceType;

    private Long parentId;

    private Long parentAppId;

    private String path;

    private String description;

    private Date createTime;

    private String createUserId;

    private String createUserName;

    private String createHost;

    public BaseResourceAuthority(String authorityType, String resourceType) {
        this.authorityType = authorityType;
        this.resourceType = resourceType;
    }

    public BaseResourceAuthority() {
    }

    public static final String ID = "id";

    public static final String AUTHORITY_ID = "authority_id";

    public static final String AUTHORITY_TYPE = "authority_type";

    public static final String RESOURCE_ID = "resource_id";

    public static final String RESOURCE_TYPE = "resource_type";

    public static final String PARENT_ID = "parent_id";

    public static final String PATH = "path";

    public static final String DESCRIPTION = "description";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

}
