package com.nuwa.client.ticket.dto.clientobject.callcenter.problem;

import com.nuwa.client.ticket.dto.clientobject.callcenter.problem.co.UpdateOnlineProblemCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "修改在线问题")
public class UpdateOnlineProblemCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    private UpdateOnlineProblemCO updateOnlineProblemCO;
}
