package com.nuwa.ticket.start.api.controller.notice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 资讯上架
 *
 * @author hy
 */
@Data
public class NoticeOffParam {
    @ApiModelProperty("Id")
    @NotNull(message = "Id不能为空")
    private Long id;
}
