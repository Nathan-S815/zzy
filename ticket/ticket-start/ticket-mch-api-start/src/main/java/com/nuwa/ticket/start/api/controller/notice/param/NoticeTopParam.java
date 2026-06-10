package com.nuwa.ticket.start.api.controller.notice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 公告置顶
 *
 * @author hy
 */
@Data
public class NoticeTopParam {

    @ApiModelProperty("id")
    private Long id;
}
