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
 * 任务消息订阅表
 *
 * @author huyonghack@163.com
 * @since 2021-12-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TaskMessageSubscribe对象")
public class TaskMessageSubscribe extends Model<TaskMessageSubscribe> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("账户列表")
    private String accountList;

    @ApiModelProperty("发送方式 dd,sms")
    private String alertType;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Integer status;

    @ApiModelProperty("通知模板id")
    private Long templateId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标志[1删除 0正常]")
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String ACCOUNT_LIST = "account_list";

    public static final String ALERT_TYPE = "alert_type";

    public static final String BIZ_CODE = "biz_code";

    public static final String TASK_ID = "task_id";

    public static final String STATUS = "status";

    public static final String TEMPLATE_ID = "template_id";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

}
