package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量修改资讯分组
 *
 * @author hy
 */
@Data
public class BatchModifyCategoryParam {

    @ApiModelProperty("ids")
    private List<Long> ids;

    @ApiModelProperty("一级分类Id")
    @NotNull(message = "一级分类Id不能为空")
    private Long categoryOne;

    @ApiModelProperty("二级分类Id")
    private Long categorySecond;
}
