package com.nuwa.discovery.start.api.controller.task.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "修改任务消息订阅参数")
public class ModifyMessageSubscribeParam {
    @ApiModelProperty("账户列表")
    private String accountList;

    @ApiModelProperty("发送方式 dd,sms")
    private String alertType;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("通知模板id")
    private Long templateId;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Integer status;
}
