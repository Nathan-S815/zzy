package com.nuwa.client.ticket.dto.clientobject.activity;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增活动命令")
public class CreateActivityCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动名称")
    private String activityTitle;

    @ApiModelProperty(value = "活动开始时间")
    private Date holdTimeStart;

    @ApiModelProperty(value = "活动结束时间")
    private Date holdTimeEnd;

    @ApiModelProperty(value = "图片地址,多个逗号隔开")
    private String imageList;

    @ApiModelProperty(value = "活动票数")
    private Integer ticketsNum;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "活动地址")
    private String address;

    @ApiModelProperty(value = "举办方")
    private String organizer;

    @ApiModelProperty(value = "活动详情")
    private String detailContentEditor;

    @ApiModelProperty(value = "温馨提示")
    private String tipContentEditor;

    @ApiModelProperty(value = "是否支持报名(0不支持 1支持)")
    private Integer isSignable;

    private Long appId;
}
