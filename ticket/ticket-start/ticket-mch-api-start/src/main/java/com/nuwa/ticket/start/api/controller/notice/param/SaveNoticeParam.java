package com.nuwa.ticket.start.api.controller.notice.param;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增公告
 *
 * @author hy
 */
@Data
public class SaveNoticeParam {
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("内容")
    private String content;
}
