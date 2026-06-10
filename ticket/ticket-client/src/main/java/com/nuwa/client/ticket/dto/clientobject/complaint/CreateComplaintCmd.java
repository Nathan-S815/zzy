package com.nuwa.client.ticket.dto.clientobject.complaint;

import com.nuwa.client.ticket.dto.clientobject.complaint.co.CreateComplaintCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增投诉")
public class CreateComplaintCmd extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    private CreateComplaintCO createComplaintCO;
}
