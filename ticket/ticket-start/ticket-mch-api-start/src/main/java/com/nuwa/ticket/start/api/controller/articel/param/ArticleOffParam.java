package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 资讯下架
 *
 * @author hy
 */
@Data
public class ArticleOffParam {
    @ApiModelProperty("Id")
    @NotNull(message = "Id不能为空")
    private Long id;
}
