package com.zzy.api.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EventListParam {

    @ApiModelProperty(value = "事件名称", required = false)
    private String eventName;

    @ApiModelProperty(value = "事件状态(1:待分配, 2:已分配待确认,3:已确认处理中,4:失效,5:结束,6:需强制指定,7:待管理员审核中,9:审核通过,10:再处理),多个用逗号分隔", required = false)
    private String eventStatus;

    @ApiModelProperty(value = "事件等级(1:一般,2:较大,3:重大,4:特别重大)", required = false)
    private Integer eventLevel;

    @ApiModelProperty(value = "事件类型ID", required = false)
    private Integer eventTypeId;

    @ApiModelProperty(value = "部门Id(被分配部门)", required = false)
    private Integer departId;

    @ApiModelProperty(value = "查询开始时间(yyyy-MM-dd)", required = false)
    private String startTime;

    @ApiModelProperty(value = "查询截止时间(yyyy-MM-dd)", required = false)
    private String endTime;


    @ApiModelProperty(hidden = true)
    private Date stTime;

    @ApiModelProperty(hidden = true)
    private Date edTime;

//    @ApiModelProperty(value = "月份(eg:2020-04),如果填写此参数则startTime&endTime参数失效", required = false)
//    private String month;


}
