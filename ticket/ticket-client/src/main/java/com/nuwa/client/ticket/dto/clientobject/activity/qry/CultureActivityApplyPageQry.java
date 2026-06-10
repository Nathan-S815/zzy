package com.nuwa.client.ticket.dto.clientobject.activity.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 文化活动报名 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-06-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文化活动报名PageQry")
public class CultureActivityApplyPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    private Long appId;
}
