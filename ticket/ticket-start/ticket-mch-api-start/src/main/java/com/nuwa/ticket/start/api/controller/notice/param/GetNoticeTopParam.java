package com.nuwa.ticket.start.api.controller.notice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取置顶公告
 *
 * @author hy
 */
@Data
public class GetNoticeTopParam {

    @ApiModelProperty("mchId")
    private Long mchId;

    @ApiModelProperty("dataType  top:置顶")
    private String dataType;
}
