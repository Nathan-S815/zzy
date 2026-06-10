package com.nuwa.client.discovery.dto.clientobject.user.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 达人任务权益表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人任务权益表PageQry")
public class MemberTaskPrizePageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String name;


    @ApiModelProperty(value = "创建时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "任务平台编码")
    private String platformCode;

    @ApiModelProperty(value = "任务状态 1:待认领 2:已认领，待发放 3:已发放 4:审核失败")
    private Integer status;

}
