package com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改在线问题")
public class UpdateOnlineProblemCO extends NuwaCO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "问题")
    private String problem;

    @ApiModelProperty(value = "问题结果")
    private String result;
}
