package com.nuwa.discovery.start.api.controller.sms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hy
 */
@Data
public class SmsTemplateVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("模板内容")
    private String content;

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("业务类型")
    private String bizCode;

    @ApiModelProperty("业务名称")
    private String bizName;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
