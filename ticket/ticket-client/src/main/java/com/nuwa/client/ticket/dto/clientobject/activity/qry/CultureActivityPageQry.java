package com.nuwa.client.ticket.dto.clientobject.activity.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 文化活动 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动PageQry")
public class CultureActivityPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动名称")
    private String activityTitle;

    @ApiModelProperty(value = "活动开始时间")
    private Date holdTimeStart;

    @ApiModelProperty(value = "活动结束时间")
    private Date holdTimeEnd;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    private Long appId;
}
