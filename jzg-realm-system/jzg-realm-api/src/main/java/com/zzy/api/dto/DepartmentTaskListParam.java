package com.zzy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentTaskListParam {

    @ApiModelProperty(value = "事件状态(-1:不显示状态,1:待分配, 2:已分配待确认,3:已确认处理中,4:失效,5:结束,6:需强制指定,7:管理员审核中,8:管理员审核不通过,9:管理员审核通过)", required = false)
    private Integer eventStatus;

    @ApiModelProperty(value = "部门Id", required = false)
    private Integer departId;

    @ApiModelProperty(value = "岗位", required = false)
    private String title;

    @ApiModelProperty(value = "开始时间(yyyy-MM-dd)", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间(yyyy-MM-dd)", required = false)
    private String endTime;


}
