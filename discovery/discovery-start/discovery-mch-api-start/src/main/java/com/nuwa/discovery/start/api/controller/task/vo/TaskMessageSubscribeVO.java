package com.nuwa.discovery.start.api.controller.task.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class TaskMessageSubscribeVO {
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

    @ApiModelProperty("通知模板内容")
    private String templateContent;
}
