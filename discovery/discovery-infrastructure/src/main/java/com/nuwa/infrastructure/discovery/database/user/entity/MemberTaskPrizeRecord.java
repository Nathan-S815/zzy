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
 * 达人任务权益提交表
 *
 * @author huyonghack@163.com
 * @since 2021-11-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MemberTaskPrizeRecord对象")
public class MemberTaskPrizeRecord extends Model<MemberTaskPrizeRecord> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("达人id")
    private Long userId;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("任务权益id")
    private String prizeTypeId;

    @ApiModelProperty("权益标题")
    private String prizeTitle;

    @ApiModelProperty("任务权益id")
    private Long taskPrizeId;

    @ApiModelProperty("我的任务权益id")
    private Long memberTaskPrizeId;

    @ApiModelProperty("作品介绍")
    private String introduceText;

    @ApiModelProperty("图片介绍")
    private String pictures;

    @ApiModelProperty("审核文字备注")
    private String remarkText;

    @ApiModelProperty("审核图片备注")
    private String remarkPictures;

    @ApiModelProperty("任务状态 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;

    @ApiModelProperty("扩展字段")
    private String extData;

    @ApiModelProperty("认领时间")
    private Date submitTime;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标志[1删除 0正常]IM")
    private Integer deleteFlag;

    @ApiModelProperty("任务平台编码")
    private String platformCode;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String TASK_ID = "task_id";

    public static final String PRIZE_TYPE_ID = "prize_type_id";

    public static final String PRIZE_TITLE = "prize_title";

    public static final String TASK_PRIZE_ID = "task_prize_id";

    public static final String MEMBER_TASK_PRIZE_ID = "member_task_prize_id";

    public static final String INTRODUCE_TEXT = "introduce_text";

    public static final String PICTURES = "pictures";

    public static final String REMARK_TEXT = "remark_text";

    public static final String REMARK_PICTURES = "remark_pictures";

    public static final String STATUS = "status";

    public static final String EXT_DATA = "ext_data";

    public static final String SUBMIT_TIME = "submit_time";

    public static final String AUDIT_TIME = "audit_time";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String PLATFORM_CODE = "platform_code";

}
