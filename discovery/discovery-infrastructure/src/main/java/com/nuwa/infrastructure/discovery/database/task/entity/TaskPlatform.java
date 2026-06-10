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
 * 任务平台表
 *
 * @author huyonghack@163.com
 * @since 2021-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TaskPlatform对象")
public class TaskPlatform extends Model<TaskPlatform> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("平台编码")
    private String code;

    @ApiModelProperty("平台名称")
    private String  platName;

    @ApiModelProperty("状态 1：开启 0：关闭")
    private Integer status;


    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String CODE = "code";

    public static final String  PLAT_NAME = " plat_name";

    public static final String STATUS = "status";

}
