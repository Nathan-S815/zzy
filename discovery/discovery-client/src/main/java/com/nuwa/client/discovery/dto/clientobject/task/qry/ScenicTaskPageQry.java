package com.nuwa.client.discovery.dto.clientobject.task.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 景区任务表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-11-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景区任务表PageQry")
public class ScenicTaskPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    private Long id;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "创建时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "任务平台编码")
    private String platformCode;

    @ApiModelProperty(value = "开始时间")
    private Date beginDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "任务状态 1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

    @ApiModelProperty(value = "奖励类型 10:团购分佣 11:探店免票 12:豆荚助力 13:现金奖励")
    private Integer prizeType;

    @ApiModelProperty("来源 1:平台 2:商户")
    private Integer source;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("联系人电话")
    private String linkmanTelephone;

    @ApiModelProperty("创建者id")
    private Long createById;


}
