package com.nuwa.ticket.start.api.controller.notice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取公告详情
 *
 * @author hy
 */
@Data
public class GetNoticeDetailParam {

    @ApiModelProperty("mchId")
    private Long mchId;

    @ApiModelProperty("id")
    private Long id;
}
