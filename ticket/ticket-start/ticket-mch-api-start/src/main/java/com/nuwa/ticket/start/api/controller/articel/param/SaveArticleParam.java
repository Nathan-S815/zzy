package com.nuwa.ticket.start.api.controller.articel.param;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增资讯
 *
 * @author hy
 */
@Data
public class SaveArticleParam {

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("内容(JSONArray)")
    private JSONArray content;

    @ApiModelProperty("一级分类Id")
    @NotNull(message = "一级分类Id不能为空")
    private Long categoryOne;

    @ApiModelProperty("二级分类Id")
    private Long categorySecond;

    @ApiModelProperty("封面图")
    @NotBlank(message = "封面图不能为空")
    private String cover;
}
