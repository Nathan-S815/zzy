package com.nuwa.client.ticket.dto.clientobject.activity.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 活动类别 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-08-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "活动类别PageQry")
public class CultureActivityCategoryPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "上下架状态[0下架 1上架]")
    private Integer publishStatus;

    private Long appId;

}
