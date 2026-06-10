package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增身份接口
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModifyOneToolLinkSortNumParam extends NuwaCommand {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("排序字段")
    private Integer sortNum;
}
