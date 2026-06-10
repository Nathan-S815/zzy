package com.nuwa.client.ticket.dto.clientobject.callcenter.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 *  PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PageQry")
public class OnlineProblemPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "提交时间")
    private String time;

    private Long appId;

}
