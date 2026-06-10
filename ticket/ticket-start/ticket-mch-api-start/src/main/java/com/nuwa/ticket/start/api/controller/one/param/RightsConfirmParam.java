package com.nuwa.ticket.start.api.controller.one.param;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RightsConfirmParam extends NuwaCommand {
    private Long id;
    private Long rightsId;
}
