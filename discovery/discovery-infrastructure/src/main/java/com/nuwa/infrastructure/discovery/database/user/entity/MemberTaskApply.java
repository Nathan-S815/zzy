package com.nuwa.infrastructure.discovery.database.user.entity;

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

import javax.validation.constraints.NotNull;

/**
 * 达人任务报名表
 *
 * @author huyonghack@163.com
 * @since 2021-11-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberTaskApply对象")
public class MemberTaskApply extends Model<MemberTaskApply> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("任务状态 1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("达人id")
    private Long userId;

    @ApiModelProperty("达人标签")
    private String memberTag;

    @ApiModelProperty("达人标签")
    private String memberTagId;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("达人地区")
    private String region;

    @ApiModelProperty("达人等级")
    private Integer userLevelId;

    @ApiModelProperty("视频")
    private String video;

    @ApiModelProperty(value = "权重值", required = true)
    private Integer weight;

    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String STATUS = "status";

    public static final String TASK_ID = "task_id";

    public static final String USER_ID = "user_id";

    public static final String MEMBER_TAG = "member_tag";

    public static final String MEMBER_TAG_ID = "member_tag_id";

    public static final String SEX = "sex";

    public static final String REGION = "region";

    public static final String USER_LEVEL_ID = "user_level_id";

    public static final String VIDEO = "video";
}
