package com.nuwa.ticket.start.api.controller.articel.param;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 资讯上架
 *
 * @author hy
 */
@Data
public class ArticleOnParam {
    @ApiModelProperty("Id")
    @NotNull(message = "Id不能为空")
    private Long id;
}
