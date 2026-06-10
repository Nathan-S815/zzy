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
 * 达人任务报名表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "达人任务报名表PageQry")
public class MemberTaskApplyPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "创建时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "任务状态 1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

}
