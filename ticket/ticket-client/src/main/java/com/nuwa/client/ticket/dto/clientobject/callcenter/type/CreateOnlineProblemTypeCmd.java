package com.nuwa.client.ticket.dto.clientobject.callcenter.type;

import com.nuwa.client.ticket.dto.clientobject.callcenter.type.co.CreateOnlineProblemTypeCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增问题类别，类型")
public class CreateOnlineProblemTypeCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;
    private CreateOnlineProblemTypeCO createOnlineProblemTypeCO;

    private Long appId;
}
