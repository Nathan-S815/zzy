package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增分类
 *
 * @author hy
 */
@Data
public class ModifyArticleCategoryParam {

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
}
