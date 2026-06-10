package com.nuwa.infrastructure.discovery.database.task.entity;

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
 * 任务权益表
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TaskPrize对象")
public class TaskPrize extends Model<TaskPrize> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("权益类型")
    private Integer prizeType;

    @ApiModelProperty("权益名称")
    private String prizeTitle;

    @ApiModelProperty("权益内容")
    private String prizeContent;

    @ApiModelProperty("状态 1：开启 0：关闭")
    private Integer status;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String TASK_ID = "task_id";

    public static final String PLATFORM_CODE = "platform_code";

    public static final String PRIZE_TYPE = "prize_type";

    public static final String PRIZE_TITLE = "prize_title";

    public static final String PRIZE_CONTENT = "prize_content";

    public static final String STATUS = "status";

}
