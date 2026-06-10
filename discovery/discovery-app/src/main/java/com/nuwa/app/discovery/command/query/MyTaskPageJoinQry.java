package com.nuwa.app.discovery.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "我的任务分页PageQry")
public class MyTaskPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("状态 2:进行中 3:已结束")
    private Integer status;
}
