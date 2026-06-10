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

/**
 * 达人任务权益表
 *
 * @author huyonghack@163.com
 * @since 2021-11-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberTaskPrize对象")
public class MemberTaskPrize extends Model<MemberTaskPrize> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("任务权益id")
    private String prizeTypeId;

    @ApiModelProperty("作品介绍")
    private String introduceText;

    @ApiModelProperty("图片介绍")
    private String pictures;

    @ApiModelProperty("审核文字备注")
    private String remarkText;

    @ApiModelProperty("审核图片备注")
    private String remarkPictures;

    @ApiModelProperty("任务状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("扩展字段")
    private String extData;

    @ApiModelProperty("达人id")
    private Long userId;

    @ApiModelProperty("认领时间")
    private Date submitTime;

    @ApiModelProperty("任务权益id")
    private Long taskPrizeId;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String PLATFORM_CODE = "platform_code";

    public static final String PRIZE_TYPE_ID = "prize_type_id";

    public static final String INTRODUCE_TEXT = "introduce_text";

    public static final String PICTURES = "pictures";

    public static final String REMARK_TEXT = "remark_text";

    public static final String REMARK_PICTURES = "remark_pictures";

    public static final String STATUS = "status";

    public static final String TASK_ID = "task_id";

    public static final String EXT_DATA = "ext_data";

    public static final String USER_ID = "user_id";

    public static final String SUBMIT_TIME = "submit_time";

    public static final String TASK_PRIZE_ID = "task_prize_id";

}
