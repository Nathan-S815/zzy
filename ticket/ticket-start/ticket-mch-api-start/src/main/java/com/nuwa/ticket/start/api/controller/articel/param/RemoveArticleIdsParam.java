package com.nuwa.ticket.start.api.controller.articel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 批量移除资讯
 *
 * @author hy
 */
@Data
public class RemoveArticleIdsParam {

    @ApiModelProperty("ids")
    private List<Long> ids;
}
