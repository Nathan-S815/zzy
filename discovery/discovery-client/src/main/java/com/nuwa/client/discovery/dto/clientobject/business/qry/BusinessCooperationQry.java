package com.nuwa.client.discovery.dto.clientobject.business.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户合作PageQry")
public class BusinessCooperationQry extends NuwaPageQry {

    @ApiModelProperty(value = "状态 1：待实施  2：实施中  3：已结束")
    private Integer status;

    @ApiModelProperty(value = "创建时间IM开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间IM结束")
    private Date createTimeEnd;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty("所属行业 1：景区 2：酒店名宿 3：休闲娱乐 4：餐饮美食 5：其他")
    private Integer industry;
}
