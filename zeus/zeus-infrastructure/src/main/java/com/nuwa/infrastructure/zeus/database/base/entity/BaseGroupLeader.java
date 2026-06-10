package com.nuwa.infrastructure.zeus.database.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BaseGroupLeader extends Model<BaseGroupLeader> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String groupId;

    private String userId;

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

    public static final String GROUP_ID = "group_id";

    public static final String USER_ID = "user_id";

    public static final String DESCRIPTION = "description";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_USER_NAME = "create_user_name";

    public static final String CREATE_HOST = "create_host";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_USER_NAME = "update_user_name";

    public static final String UPDATE_HOST = "update_host";

}
