package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 移除资讯分类
 *
 * @author hy
 */
@Data
public class RemoveArticleCategoryParam {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
}
