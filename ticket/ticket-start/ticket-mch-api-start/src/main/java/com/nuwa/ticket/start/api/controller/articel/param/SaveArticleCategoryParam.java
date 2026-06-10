package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增分类
 *
 * @author hy
 */
@Data
public class SaveArticleCategoryParam {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("父id 顶级分类不传值")
    private Integer parentId;
}
