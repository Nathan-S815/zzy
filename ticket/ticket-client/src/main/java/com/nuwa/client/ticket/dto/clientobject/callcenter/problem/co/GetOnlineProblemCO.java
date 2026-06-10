package com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "根据id获取在线问题")
public class GetOnlineProblemCO extends NuwaCO {
    @ApiModelProperty(value = "主键id")
    private Long id;
}
